package PolosServ.features.world


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureworldRouting() {
    routing {
        get("/world") {
            val worldController = worldController(call)
            worldController.getworld()
        }
    }
}