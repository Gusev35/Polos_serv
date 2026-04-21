package PolosServ.features.daily_questions


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configuredailyRouting() {
    routing {
        get("/daily_questions") {
            val dailyController = dailyController(call)
            dailyController.getdaily()
        }
    }
}