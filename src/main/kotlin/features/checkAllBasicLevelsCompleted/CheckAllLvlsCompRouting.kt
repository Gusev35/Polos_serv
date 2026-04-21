package PolosServ.features.checkAllBasicLevelsCompleted


import io.ktor.server.application.*

import io.ktor.server.routing.*


fun Application.configureCheckAllLvlsCompRouting() {
    routing {
        get("/checkAllBasicLevelsCompleted") {
            val CheckAllLvlsCompController = CheckAllLvlsCompController(call)
            CheckAllLvlsCompController.CheckAllLvlsComp()
        }
    }
}