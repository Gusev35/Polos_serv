package PolosServ.features.Rating
import kotlinx.serialization.Serializable

@Serializable
data class RatingReceiveRemote(
    val user_id: Int,
)

@Serializable
data class RatingResponseRemote(
    val firstName: String,
    val lastName: String,
    val totalPoints: Int
)