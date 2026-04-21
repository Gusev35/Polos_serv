package PolosServ.Database.level

class LevelDTO (
    val id: Int,
    val subject_id: Int,
    val description: String,
    val is_locked: Boolean,
    val map_position_x: Int,
    val map_position_y: Int
)