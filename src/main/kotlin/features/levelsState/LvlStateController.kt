    package PolosServ.features.levelsState

    import PolosServ.Database.Subjects.Subject
    import PolosServ.Database.Teacher.Teacher
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class LvlStateController(private val call: ApplicationCall) {
        suspend fun getLvlState() {
            try {
                val receive = call.receive<LvlStateReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val aboutlvls = Subject.LvlState(receive.subject_id, receive.user_id)

                if (aboutlvls.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound, "No levels found for this user")
                    return
                }

                call.respond(aboutlvls)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
