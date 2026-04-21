package PolosServ.features.rewardsClaim


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configurerewardsClaimRouting() {
    routing {
        get("/rewardsClaim") {
            val rewardsClaimController = rewardsClaimController(call)
            rewardsClaimController.getLvl()
        }
    }
}