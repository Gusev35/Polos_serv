    package PolosServ.features.rewardsClaim

    import io.ktor.http.*
    import PolosServ.Database.Progress.User_Progress
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class rewardsClaimController(private val call: ApplicationCall) {
        suspend fun getLvl() {
            try {
                val receive = call.receive<rewardsClaimReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = User_Progress.rewardsClaimUserLevels(receive.user_id,receive.question_id, receive.correct_answers_count)
                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
