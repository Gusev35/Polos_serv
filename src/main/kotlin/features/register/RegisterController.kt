package PolosServ.features.register


import PolosServ.Database.Tokens.TokenDTO
import PolosServ.Database.users.UserDTO
import PolosServ.Database.Tokens.Tokens
import PolosServ.Database.users.User
import PolosServ.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

class RegisterController(private val call: ApplicationCall) {
    suspend fun registerNewUser(){
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.BadRequest,"Email is not invalid")
        }
        val userDTO=User.fetchUser(registerReceiveRemote.login)
        if(userDTO!=null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }else{
            val token = UUID.randomUUID().toString()

            try {
                User.insert(
                    UserDTO(
                        password = registerReceiveRemote.password,
                        email = registerReceiveRemote.email,
                        firstName = registerReceiveRemote.firstName,
                        lastName = registerReceiveRemote.lastName,
                        group = registerReceiveRemote.group,
                        id = registerReceiveRemote.id
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Can't create user ${e.localizedMessage}")
            }
            Tokens.insert(
                TokenDTO(
                    token = token,
                    id = registerReceiveRemote.id,
                    user_id = registerReceiveRemote.id
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}