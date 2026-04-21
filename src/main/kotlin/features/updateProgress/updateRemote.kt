package PolosServ.features.updateProgress
import kotlinx.serialization.Serializable

@Serializable
data class updateReceiveRemote(
    val user_id: Int,
    val level_id: Int,
    val question_id: Int,
    val is_correct: Boolean
)

@Serializable
data class updateResponseRemote(
    val rabotaet: Boolean
)