    package PolosServ.features.daily_questions

    import PolosServ.Database.question.Question
    import PolosServ.features.daily_questions.FetchResponseRemote

    import org.jetbrains.exposed.sql.SqlExpressionBuilder.random
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class dailyController(private val call: ApplicationCall) {
        suspend fun getdaily() {
            try {
                val receive = call.receive<dailyReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = Question.dailyque()

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
