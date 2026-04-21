package PolosServ.Database.level

import PolosServ.Database.Progress.ProgressDTO
import PolosServ.features.fetchLevels.FetchResponseRemote
import PolosServ.features.checkLevelAccess.LvlAccResponseRemote
import PolosServ.Database.Progress.User_Progress
import PolosServ.features.updateProgress.updateResponseRemote
import PolosServ.features.world.worldResponseRemote
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Level : Table("level") {
    val id = integer("id").autoIncrement()
    val subject_id = integer("subject_id")
    val description = text("description")
    val is_locked = bool("is_locked")
    val map_position_x = integer("map_position_x")
    val map_position_y = integer("map_position_y")

    fun fetchUserLevels(user_id: Int): List<FetchResponseRemote> {
        return try {
            transaction {
                // Получаем все уровни
                val allLevels = Level.selectAll().map {
                    LevelDTO(
                        id = it[Level.id],
                        subject_id = it[subject_id],
                        description = it[description],
                        is_locked = it[is_locked],
                        map_position_x = it[map_position_x],
                        map_position_y = it[map_position_y]
                    )
                }

                // Получаем прогресс пользователя
                val userProgress = User_Progress
                    .select { User_Progress.user_id eq user_id }
                    .groupBy { it[User_Progress.level_id] }
                    .mapValues { (_, rows) ->
                        rows.any { it[User_Progress.is_correct] }
                    }

                // Формируем ответ
                allLevels.map { level ->
                    val hasCompleted = userProgress[level.id] ?: false
                    FetchResponseRemote(
                        userId = user_id,
                        level_id = level.id,
                        is_locked = !hasCompleted && level.is_locked
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    fun LvlAccUserLevels(user_id: Int, level_id: Int): LvlAccResponseRemote {
        return try {
            transaction {
                // Получаем запрошенный уровень
                val level = Level.select { Level.id eq level_id }
                    .singleOrNull()
                    ?: return@transaction LvlAccResponseRemote(is_locked = true)
                // Проверяем прогресс пользователя для этого уровня
                val hasCompleted = User_Progress
                    .select {
                        (User_Progress.user_id eq user_id) and
                                (User_Progress.level_id eq level_id) and
                                (User_Progress.is_correct eq true)
                    }
                    .any()
                LvlAccResponseRemote(
                    is_locked = !hasCompleted && level[Level.is_locked]
                )
            }
        } catch (e: Exception) {
            LvlAccResponseRemote(is_locked = true)
        }
    }
    fun aboutworld(subject_id: Int): List<worldResponseRemote> {
        return try {
            transaction {
                Level.select { Level.subject_id eq subject_id }.map { row ->
                    worldResponseRemote(
                        description = row[Level.description],
                        map_position_x = row[Level.map_position_x],
                        map_position_y = row[Level.map_position_y]
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}