package PolosServ.Database.users

class UserDTO (
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val group: String,
    val id: Int
)
class TopUserDTO(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val points: Int
)

