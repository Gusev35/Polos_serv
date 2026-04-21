package PolosServ.features.levelsState


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureLvlStateRouting() {
    routing {
        get("/LvlState") {
            val LvlStateController = LvlStateController(call)
            LvlStateController.getLvlState()
        }
    }
}