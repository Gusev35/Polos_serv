package PolosServ.features.teacher


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureteacherRouting() {
    routing {
        get("/teacher") {
            val teacherController = teacherController(call)
            teacherController.getteacher()
        }
    }
}