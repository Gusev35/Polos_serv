package PolosServ.features.rewardsClaim
import kotlinx.serialization.Serializable

@Serializable
data class rewardsClaimReceiveRemote(
    val user_id: Int,
    val question_id: Int,
    val correct_answers_count: Int

)

@Serializable
data class rewardsClaimResponseRemote(
    val srabotalo: Boolean
)