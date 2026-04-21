package PolosServ

import PolosServ.features.Disciplines.configureDisRouting
import PolosServ.features.login.configureLoginRouting
import PolosServ.features.register.configureRegisterRouting
import PolosServ.features.updateProgress.configureupdateRouting
import PolosServ.features.checkLevelAccess.configureLvlAccRouting
import PolosServ.features.fetchLevels.configureFetchRouting
import PolosServ.features.Rating.configureRatingRouting
import  PolosServ.features.Statistics.configureStatRouting
import PolosServ.features.checkAllBasicLevelsCompleted.configureCheckAllLvlsCompRouting
import PolosServ.features.fishRestoration.configurefishRestRouting
import PolosServ.features.levelsState.configureLvlStateRouting
import PolosServ.features.rewardsClaim.configurerewardsClaimRouting
import PolosServ.features.teacher.configureteacherRouting
import PolosServ.features.updateinf.configureupdateinfRouting
import PolosServ.features.world.configureworldRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url="jdbc:postgresql://localhost:5432/polos",
        driver = "org.postgresql.Driver",
        user="postgres",
        password = "password",

    )


    embeddedServer(CIO, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
fun Application.module() {
    configureSerialization()
    configureLoginRouting()
    configureRegisterRouting()
    configureRouting()
    configureRatingRouting()
    configureStatRouting()
    configureDisRouting()
    configureFetchRouting()
    configureLvlAccRouting()
    configureupdateRouting()
    configureCheckAllLvlsCompRouting()
    configureteacherRouting()
    configureLvlStateRouting()
    configurerewardsClaimRouting()
    configureworldRouting()
    configurefishRestRouting()
    configureupdateinfRouting()
}