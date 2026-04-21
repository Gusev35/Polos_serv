package PolosServ.features.Disciplines
import PolosServ.Database.Subjects.SubDTO
import kotlinx.serialization.Serializable

@Serializable
class DisReceiveRemote(
    val name: String,

)
@Serializable
class DisResponseRemote(
    val disciplines: List<DisciplineDTO>
)
@Serializable
class DisciplineDTO(
    val id: Int,
    val name: String,
    val description: String?
)