package PolosServ.features.login
import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val login: String,
    val password: String,
    val id: Int
)

@Serializable
data class LoginResponseRemote(
    val token: String
)