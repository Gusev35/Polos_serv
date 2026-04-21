package PolosServ.features.checkAllBasicLevelsCompleted
import kotlinx.serialization.Serializable

@Serializable
data class CheckAllLvlsCompReceiveRemote(
    val user_id: Int
)

@Serializable
data class CheckAllLvlsCompResponseRemote(
    val AllComp: Boolean
)