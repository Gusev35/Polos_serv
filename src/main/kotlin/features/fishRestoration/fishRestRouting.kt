package PolosServ.features.fishRestoration


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configurefishRestRouting() {
    routing {
        get("/restore_fish") {
            val fishRestController = fishRestController(call)
            fishRestController.fishRest()
        }
    }
}