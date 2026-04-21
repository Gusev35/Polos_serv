package PolosServ.features.teacher
import kotlinx.serialization.Serializable

@Serializable
data class teacherReceiveRemote(
    val subject_id: Int
)

@Serializable
data class teacherResponseRemote(
    val userId: Int,
    val first_name: String,
    val last_name: String,
    val email_phone: String
)