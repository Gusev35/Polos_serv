    package PolosServ.features.world

    import PolosServ.Database.Teacher.Teacher
    import PolosServ.Database.level.Level
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*


    class worldController(private val call: ApplicationCall) {
        suspend fun getworld() {
            try {
                val receive = call.receive<worldReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val aboutTeacher = Level.aboutworld(receive.subject_id)

                if (aboutTeacher.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound, "No levels found for this user")
                    return
                }

                call.respond(aboutTeacher)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
