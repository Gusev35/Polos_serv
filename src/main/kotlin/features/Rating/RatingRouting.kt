package PolosServ.features.Rating


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureRatingRouting() {
    routing {
        get("/Rating") {
            val ratingController = RatingController(call)
            ratingController.getRatingForAllUsers()
        }
    }
}