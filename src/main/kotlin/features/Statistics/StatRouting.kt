package PolosServ.features.Statistics


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureStatRouting() {
    routing {
        get("/Statistic") {
            val StatController = StatController(call)
            StatController.getStatistic()
        }
    }
}