package PolosServ.features.Statistics
import kotlinx.serialization.Serializable

@Serializable
data class StatReceiveRemote(
    val user_id: Int
)

@Serializable
data class StatResponseRemote(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val group: String?,
    val completedLevels: Int,
    val currentLevel: Int,
    val currentWorld: Int,
    val rank: Int
)