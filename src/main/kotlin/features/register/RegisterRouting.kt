package PolosServ.features.register


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureRegisterRouting() {
    routing {
        get("/register") {
            val RegisterController = RegisterController(call)
            RegisterController.registerNewUser()
        }
    }

}