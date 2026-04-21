package PolosServ.features.fetchLevels
import kotlinx.serialization.Serializable

@Serializable
data class FetchReceiveRemote(
    val user_id: Int
)

@Serializable
data class FetchResponseRemote(
    val userId: Int,
    val level_id: Int,
    val is_locked: Boolean
)