package PolosServ.features.updateProgress


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureupdateRouting() {
    routing {
        get("/updateProgress") {
            val updateController = updateController(call)
            updateController.getLvl()
        }
    }
}