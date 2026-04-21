    package PolosServ.features.checkAllBasicLevelsCompleted

    import io.ktor.http.*
    import PolosServ.Database.Progress.User_Progress
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class CheckAllLvlsCompController(private val call: ApplicationCall) {
        suspend fun CheckAllLvlsComp() {
            try {
                val receive = call.receive<CheckAllLvlsCompReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userCompLevels = User_Progress.CheckAllLvlsCompByUser(receive.user_id)
                call.respond(userCompLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
