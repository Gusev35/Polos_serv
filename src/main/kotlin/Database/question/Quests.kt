package PolosServ.Database.question


import PolosServ.Database.Progress.User_Progress.question_id
import org.jetbrains.exposed.sql.Table
import PolosServ.features.daily_questions.dailyResponseRemote
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.selectAll

object Question : Table("question") {
    val id = integer("id").autoIncrement()
    val level_id = integer("level_id")
    val question_type = text("question_type")
    val question_text = text("question_text")
    val correct_answer = text("correct_answer")
    val options = text("options")
    val feedback_correct = text("feedback_correct")
    val feedback_incorrect = text("feedback_incorrect")

    fun dailyque(): List<dailyResponseRemote> {
        return try {
            transaction {
                // 1. Сначала получаем общее количество вопросов
                val totalQuestions = Question.selectAll().count()

                // 2. Генерируем случайный индекс (от 0 до totalQuestions-1)
                val randomIndex = (0 until totalQuestions).random()

                // 3. Выбираем вопрос по случайному индексу
                Question
                    .selectAll()
                    .limit(1, randomIndex.toLong())  // Пропускаем randomIndex записей
                    .map {
                        dailyResponseRemote(
                            question_id = it[Question.id],
                            question_text = it[Question.question_text],
                            question_id
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}