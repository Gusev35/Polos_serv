    package PolosServ.features.checkLevelAccess

    import PolosServ.Database.level.Level
    import com.typesafe.config.ConfigException.Null
    import io.ktor.http.*
    import io.ktor.server.application.*
    import io.ktor.server.request.*
    import io.ktor.server.response.*



    class LvlAccController(private val call: ApplicationCall) {
        suspend fun getLvl() {
            try {
                val receive = call.receive<LvlAccReceiveRemote>()

                // Получаем уровни пользователя (только необходимые поля)
                val userLevels = Level.LvlAccUserLevels(receive.user_id,receive.level_id)

             

                call.respond(userLevels)

            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
            }
        }
    }
