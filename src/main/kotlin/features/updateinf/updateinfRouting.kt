package PolosServ.features.updateinf


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureupdateinfRouting() {
    routing {
        get("/Userupdate") {
            val updateinfController = updateinfController(call)
            updateinfController.getinfLvl()
        }
    }
}