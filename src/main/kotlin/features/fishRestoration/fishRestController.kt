    package PolosServ.features.fishRestoration

    import io.ktor.http.*
    import PolosServ.Database.Progress.User_Progress
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class fishRestController(private val call: ApplicationCall) {
        suspend fun fishRest() {
            try {
                val receive = call.receive<fishRestReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = User_Progress.fishRestUser(receive.user_id,receive.level_id,receive.question_id,receive.is_correct)
                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
