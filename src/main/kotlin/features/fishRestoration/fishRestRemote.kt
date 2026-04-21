package PolosServ.features.fishRestoration
import kotlinx.serialization.Serializable

@Serializable
data class fishRestReceiveRemote(
    val user_id: Int,
    val level_id: Int,
    val question_id: Int,
    val is_correct: Boolean
)

@Serializable
data class fishRestResponseRemote(
    val working: Boolean
)