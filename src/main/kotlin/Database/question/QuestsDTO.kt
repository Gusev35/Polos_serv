package PolosServ.Database.question

class QuestsDTO(
    val id: Int,
    val level_id: Int,
    val question_type: String,
    val question_text: String,
    val correct_answer: String,
    val options: String,
    val feedback_correct: String,
    val feedback_incorrect: String
)