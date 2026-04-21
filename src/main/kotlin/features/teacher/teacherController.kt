    package PolosServ.features.teacher

    import PolosServ.Database.Teacher.Teacher
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class teacherController(private val call: ApplicationCall) {
        suspend fun getteacher() {
            try {
                val receive = call.receive<teacherReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val aboutTeacher = Teacher.aboutTeacher(receive.subject_id)

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
