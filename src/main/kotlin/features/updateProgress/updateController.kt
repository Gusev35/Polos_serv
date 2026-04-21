    package PolosServ.features.updateProgress

    import PolosServ.Database.level.Level
    import io.ktor.http.*
    import PolosServ.Database.Progress.User_Progress
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class updateController(private val call: ApplicationCall) {
        suspend fun getLvl() {
            try {
                val receive = call.receive<updateReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = User_Progress.updateUserLevels(receive.user_id,receive.level_id,receive.question_id,receive.is_correct)
                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
