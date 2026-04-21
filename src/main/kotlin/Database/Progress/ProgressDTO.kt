package PolosServ.Database.Progress

class ProgressDTO(
    val user_id: Int,
    val level_id: Int,
    val question_id: Int,
    val is_correct: Boolean,
    val attempts: Int,
    val has_recovered_fish: Boolean,
    val points_earned: Int
)