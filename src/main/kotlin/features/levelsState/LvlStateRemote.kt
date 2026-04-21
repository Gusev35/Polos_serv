package PolosServ.features.levelsState
import kotlinx.serialization.Serializable

@Serializable
data class LvlStateReceiveRemote(
    val subject_id: Int,
    val user_id: Int
)

@Serializable
data class LvlStateResponseRemote(
    val user_id: Int,
    val level_id: Int,
    val is_correct: Boolean,
)