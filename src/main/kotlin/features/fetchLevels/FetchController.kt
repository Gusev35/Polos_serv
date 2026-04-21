    package PolosServ.features.fetchLevels

    import PolosServ.Database.level.Level
    import PolosServ.Database.level.LevelDTO
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class FetchController(private val call: ApplicationCall) {
        suspend fun getLvls() {
            try {
                val receive = call.receive<FetchReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = Level.fetchUserLevels(receive.user_id)

                if (userLevels.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound, "No levels found for this user")
                    return
                }

                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
