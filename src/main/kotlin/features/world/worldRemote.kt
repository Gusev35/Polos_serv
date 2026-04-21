package PolosServ.features.world
import jdk.jfr.Description
import kotlinx.serialization.Serializable

@Serializable
data class worldReceiveRemote(
    val subject_id: Int
)

@Serializable
data class worldResponseRemote(
    val description: String,
    val map_position_x: Int,
    val map_position_y: Int
)