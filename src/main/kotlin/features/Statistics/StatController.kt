package PolosServ.features.Statistics
import PolosServ.Database.Progress.User_Progress
import PolosServ.Database.users.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.transactions.transaction

class StatController(private val call: ApplicationCall) {
    private val rankingCache = mutableMapOf<Int, Int>()
    private var lastUpdateTime = 0L

    suspend fun getStatistic() {
        try {
            val receive = call.receive<StatReceiveRemote>()

            // Получаем данные пользователя
            val user = User.fetchUserById(receive.user_id) ?: run {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return
            }

            // Получаем прогресс пользователя
            val userProgress = User_Progress.fetchAllUserProgress(receive.user_id)
            val userTotalPoints = userProgress.sumOf { it.points_earned }

            // Получаем позицию в рейтинге
            val userRank = getGlobalRank(receive.user_id)

            // Рассчитываем уровень и мир
            val completedLevels = userProgress.distinctBy { it.level_id }.count()
            val currentLevel = (userProgress.maxOfOrNull { it.level_id } ?: 0) + 1
            val currentWorld = (currentLevel - 1) / 10 + 1

            call.respond(
                StatResponseRemote(
                    userId = user.id,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    group = user.group,
                    completedLevels = completedLevels,
                    currentLevel = currentLevel,
                    currentWorld = currentWorld,
                    rank = userRank
                )
            )

        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
        }
    }

    private fun getGlobalRank(userId: Int): Int {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastUpdateTime > 300_000) { // 5 минут кэш
            updateRankingCache()
            lastUpdateTime = currentTime
        }
        return rankingCache[userId] ?: 0
    }

    private fun updateRankingCache() {
        transaction {
            val userPoints = User_Progress
                .slice(User_Progress.user_id, User_Progress.points_earned.sum())
                .selectAll()
                .groupBy(User_Progress.user_id)  // Явное указание GROUP BY
                .map {
                    val userId = it[User_Progress.user_id]
                    val points = it[User_Progress.points_earned.sum()] ?: 0
                    userId to points
                }

            // Сортировка по убыванию очков
            val sortedUsers = userPoints
                .sortedByDescending { it.second }
                .mapIndexed { index, (userId, _) -> userId to index + 1 }

            rankingCache.clear()
            rankingCache.putAll(sortedUsers)
        }
    }
}