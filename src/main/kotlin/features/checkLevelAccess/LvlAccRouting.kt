package PolosServ.features.checkLevelAccess


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureLvlAccRouting() {
    routing {
        get("/checkLevelAccess") {
            val LvlAccController = LvlAccController(call)
            LvlAccController.getLvl()
        }
    }
}