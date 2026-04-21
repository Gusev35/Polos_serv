package PolosServ.features.daily_questions
import kotlinx.serialization.Serializable

@Serializable
data class dailyReceiveRemote(
    val user_id: Int
)

@Serializable
data class dailyResponseRemote(
    val question_id: String,
    val question_text: String,
    val correct_answer: String

)