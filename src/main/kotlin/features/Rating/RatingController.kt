package PolosServ.features.Rating

import PolosServ.Database.Progress.ProgressDTO
import PolosServ.Database.Progress.User_Progress
import PolosServ.Database.users.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

class RatingController(private val call: ApplicationCall) {
    suspend fun getRatingForAllUsers() {
        try {
            // Получаем всех пользователей
            val allUsers = User.fetchAllUsers()

            // Для каждого пользователя получаем его прогресс и считаем общие очки
            val ratingList = allUsers.map { user ->
                val progressList = User_Progress.fetchAllUserProgress(user.id)
                val totalPoints = progressList.sumOf { it.points_earned }

                RatingResponseRemote(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    totalPoints = totalPoints
                )
            }

            // Сортируем по убыванию очков
            val sortedRating = ratingList.sortedByDescending { it.totalPoints }

            call.respond(sortedRating)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Error: ${e.message}")
        }
    }
}