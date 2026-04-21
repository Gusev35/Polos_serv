package PolosServ.features.fetchLevels


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureFetchRouting() {
    routing {
        get("/fetchLevels") {
            val FetchController = FetchController(call)
            FetchController.getLvls()
        }
    }
}