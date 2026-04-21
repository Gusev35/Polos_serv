    package PolosServ.features.updateinf

    import io.ktor.http.*
    import PolosServ.Database.users.User

    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class updateinfController(private val call: ApplicationCall) {
        suspend fun getinfLvl() {
            try {
                val receive = call.receive<updateinfReceiveRemote>()

                val userLevels = User.updateUserInfo(receive.userId,receive.firstName,receive.lastName,receive.group)
                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
