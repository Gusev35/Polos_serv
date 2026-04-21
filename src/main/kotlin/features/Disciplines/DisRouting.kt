package PolosServ.features.Disciplines


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureDisRouting() {
    routing {
        get("/Disciplines") {
            val DisController = DisController(call)
            DisController.performDis()
        }
    }
}