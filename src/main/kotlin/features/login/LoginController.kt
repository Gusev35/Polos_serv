package PolosServ.features.login
import PolosServ.Database.Tokens.TokenDTO
import PolosServ.Database.Tokens.Tokens
import PolosServ.Database.users.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin(){
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO=User.fetchUser(receive.login)
        println("receive $receive, UserDTO $userDTO")
        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User Not Found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}