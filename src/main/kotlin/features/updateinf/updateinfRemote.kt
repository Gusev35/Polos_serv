package PolosServ.features.updateinf
import kotlinx.serialization.Serializable

@Serializable
data class updateinfReceiveRemote(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val group: String
)

@Serializable
data class updateinfResponseRemote(
    val infUpdated: Boolean
)