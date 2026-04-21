package PolosServ.Database.Progress

import PolosServ.features.updateProgress.updateResponseRemote
import PolosServ.features.checkAllBasicLevelsCompleted.CheckAllLvlsCompResponseRemote
import PolosServ.features.rewardsClaim.rewardsClaimResponseRemote
import PolosServ.features.fishRestoration.fishRestResponseRemote
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object User_Progress : Table("user_progress") {
    val id = integer("id").autoIncrement()
    val user_id = integer("user_id")
    val level_id = integer("level_id")
    val question_id = integer("question_id")
    val is_correct = bool("is_correct")
    val attempts = integer("attempts")
    val has_covered_fish = bool("has_recovered_fish")
    val points_earned = integer("points_earned")


    fun updateUserLevels(user_id: Int, level_id: Int, question_id: Int, is_correct: Boolean): updateResponseRemote {
        return try {
            transaction {
                // Проверяем существующую запись
                val existing = User_Progress.select {
                    (User_Progress.user_id eq user_id) and
                            (User_Progress.level_id eq level_id) and
                            (User_Progress.question_id eq question_id)
                }.firstOrNull()

                if (existing != null) {
                    // Обновляем существующую запись
                    User_Progress.update({
                        (User_Progress.user_id eq user_id) and
                                (User_Progress.level_id eq level_id) and
                                (User_Progress.question_id eq question_id)
                    }) {
                        it[this.is_correct] = is_correct
                        it[this.attempts] = existing[User_Progress.attempts] + 1
                        it[this.points_earned] = 0
                    }
                    updateResponseRemote(rabotaet = true)
                } else {
                    // Вставляем новую запись
                    val insertedId = User_Progress.insert {
                        it[this.user_id] = user_id
                        it[this.level_id] = level_id
                        it[this.question_id] = question_id
                        it[this.is_correct] = is_correct
                        it[this.attempts] = 1
                        it[this.has_covered_fish] = false
                        it[this.points_earned] = 0
                    }
                    updateResponseRemote(rabotaet = true)
                }
            }
        } catch (e: Exception) {
            updateResponseRemote(
                rabotaet = false
            )
        }
    }

    fun fetchAllUserProgress(user_id: Int): List<ProgressDTO> {
        return try {
            transaction {
                User_Progress.select { User_Progress.user_id.eq(user_id) }
                    .map {
                        ProgressDTO(
                            user_id = it[User_Progress.user_id],
                            level_id = it[User_Progress.level_id],
                            question_id = it[User_Progress.question_id],
                            is_correct = it[User_Progress.is_correct],
                            attempts = it[User_Progress.attempts],
                            has_recovered_fish = it[User_Progress.has_covered_fish],
                            points_earned = it[User_Progress.points_earned]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun CheckAllLvlsCompByUser(user_id: Int): CheckAllLvlsCompResponseRemote {
        return try {
            transaction {
                // 1. Получаем все уникальные question_id, которые существуют в системе
                val allQuestionIds = User_Progress
                    .slice(User_Progress.question_id)
                    .selectAll()
                    .distinctBy { it[User_Progress.question_id] }
                    .map { it[User_Progress.question_id] }

                // 2. Получаем question_id, которые пользователь выполнил правильно
                val completedQuestionIds = User_Progress
                    .select {
                        (User_Progress.user_id eq user_id) and
                                (User_Progress.is_correct eq true)
                    }
                    .distinctBy { it[User_Progress.question_id] }
                    .map { it[User_Progress.question_id] }

                // 3. Проверяем, что пользователь выполнил все существующие вопросы
                val allCompleted = completedQuestionIds.containsAll(allQuestionIds)

                CheckAllLvlsCompResponseRemote(AllComp = allCompleted)
            }
        } catch (e: Exception) {
            CheckAllLvlsCompResponseRemote(AllComp = false)
        }
    }

    fun rewardsClaimUserLevels(user_id: Int, question_id: Int, points: Int): rewardsClaimResponseRemote {
        return try {
            transaction {
                // Проверяем, существует ли запись для обновления
                val existingRecord = User_Progress.select {
                    (User_Progress.user_id eq user_id) and
                            (User_Progress.question_id eq question_id)
                }.singleOrNull()

                if (existingRecord != null) {
                    // Обновляем существующую запись
                    User_Progress.update({
                        (User_Progress.user_id eq user_id) and
                                (User_Progress.question_id eq question_id)
                    }) {
                        it[User_Progress.points_earned] = points
                    }
                    rewardsClaimResponseRemote(srabotalo = true)
                } else {
                    // Создаем новую запись, если не существует
                    User_Progress.insert {
                        it[User_Progress.user_id] = user_id
                        it[User_Progress.question_id] = question_id
                        it[User_Progress.points_earned] = points
                        // Устанавливаем значения по умолчанию для других полей
                        it[User_Progress.is_correct] = false
                        it[User_Progress.attempts] = 0
                        it[User_Progress.has_covered_fish] = false
                    }
                    rewardsClaimResponseRemote(srabotalo = true)
                }
            }
        } catch (e: Exception) {
            // Логируем ошибку для отладки
            println("Error in rewardsClaimUserLevels: ${e.message}")
            rewardsClaimResponseRemote(srabotalo = false)
        }
    }

    fun fishRestUser(user_id: Int, level_id: Int, question_id: Int, is_correct: Boolean): fishRestResponseRemote {
        return try {
            transaction {
                // Проверяем существующую запись
                val existing = User_Progress.select {
                    (User_Progress.user_id eq user_id) and
                            (User_Progress.level_id eq level_id) and
                            (User_Progress.question_id eq question_id)
                }.firstOrNull()

                if (existing != null) {
                    // Обновляем существующую запись
                    User_Progress.update({
                        (User_Progress.user_id eq user_id) and
                                (User_Progress.level_id eq level_id) and
                                (User_Progress.question_id eq question_id)
                    }) {
                        it[this.is_correct] = is_correct
                        it[this.attempts] = existing[User_Progress.attempts] + 1
                        it[this.points_earned] = 0
                        it[this.has_covered_fish] = false
                        if(is_correct) {
                        it[this.has_covered_fish] = true}
                    }
                    fishRestResponseRemote(working = true)
                } else {
                    // Вставляем новую запись
                    val insertedId = User_Progress.insert {
                        it[this.user_id] = user_id
                        it[this.level_id] = level_id
                        it[this.question_id] = question_id
                        it[this.is_correct] = is_correct
                        it[this.attempts] = 1
                        it[this.has_covered_fish] = false
                        if(is_correct) {
                            it[this.has_covered_fish] = true}
                        it[this.points_earned] = 0
                    }
                    fishRestResponseRemote(working = true)
                }
            }
        } catch (e: Exception) {
            fishRestResponseRemote(
                working = false
            )
        }
    }


}