package PolosServ.features.login


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureLoginRouting() {
    routing {
        get("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}