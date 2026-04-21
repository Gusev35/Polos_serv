package PolosServ.features.Disciplines
import PolosServ.Database.Subjects.Subject
import io.ktor.server.application.*

import io.ktor.server.response.*

class DisController(private val call: ApplicationCall) {
    suspend fun performDis(){
        val disciplines =Subject.fetchAllDisciplines()
        call.respond(DisResponseRemote(disciplines))
    }
}