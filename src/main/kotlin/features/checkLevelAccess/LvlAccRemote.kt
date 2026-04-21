package PolosServ.features.checkLevelAccess
import kotlinx.serialization.Serializable

@Serializable
data class LvlAccReceiveRemote(
    val user_id: Int,
    val level_id: Int
)

@Serializable
data class LvlAccResponseRemote(
    val is_locked: Boolean
)