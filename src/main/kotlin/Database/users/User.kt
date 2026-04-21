package PolosServ.Database.users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.insert
import PolosServ.Database.Progress.User_Progress
import PolosServ.features.updateinf.updateinfResponseRemote
import PolosServ.features.rewardsClaim.rewardsClaimResponseRemote
import org.jetbrains.exposed.sql.transactions.transaction

object User : Table("user") {
    val id = User.integer("id").autoIncrement()
    val email = User.text("email-phone")
    val firstName = User.text("first_name")
    val lastName = User.text("last_name")
    val group = User.text("group")
    val password = User.text("password")
    fun insert(userDTO: UserDTO) {
        transaction {
            User.insert {
                it[id] = userDTO.id
                it[email] = userDTO.email
                it[firstName] = userDTO.firstName
                it[lastName] = userDTO.lastName
                it[group] = userDTO.group
                it[password] = userDTO.password
            }
        }
    }

    fun fetchUser(email: String): UserDTO? {
        return try {
            transaction {
                val user = User.select { User.email.eq(email) }.single()
                UserDTO(
                    email = user[User.email],
                    firstName = user[User.firstName],
                    lastName = user[User.lastName],
                    group = user[User.group],
                    password = user[User.password],
                    id = user[User.id]
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun fetchUserById(id: Int): UserDTO? {
        return try {
            transaction {
                val user = User.select { User.id.eq(id) }.single()
                UserDTO(
                    email = user[User.email],
                    firstName = user[User.firstName],
                    lastName = user[User.lastName],
                    group = user[User.group],
                    password = user[User.password],
                    id = user[User.id]
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getTopUsers(limit: Int): List<TopUserDTO> {
        return transaction {
            // Создаем подзапрос для суммы очков по пользователям
            val userPoints = User_Progress
                .slice(User_Progress.user_id, User_Progress.points_earned.sum())
                .selectAll()
                .groupBy(User_Progress.user_id)  // Добавляем GROUP BY
                .alias("user_points")

            // Основной запрос с JOIN к таблице пользователей
            (User innerJoin userPoints)
                .slice(
                    User.id,
                    User.firstName,
                    User.lastName,
                    userPoints[User_Progress.points_earned.sum()]
                )
                .selectAll()
                .orderBy(userPoints[User_Progress.points_earned.sum()], SortOrder.DESC)
                .limit(limit)
                .map {
                    TopUserDTO(
                        userId = it[User.id],
                        firstName = it[User.firstName],
                        lastName = it[User.lastName],
                        points = it[userPoints[User_Progress.points_earned.sum()]] ?: 0
                    )
                }
        }
    }

    fun fetchAllUsers(): List<UserDTO> {
        return try {
            transaction {
                User.selectAll().map {
                    UserDTO(
                        email = it[User.email],
                        firstName = it[User.firstName],
                        lastName = it[User.lastName],
                        group = it[User.group],
                        password = it[User.password],
                        id = it[User.id]
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun updateUserInfo(
        user_id: Int,
        first_name: String,
        last_name: String,
        group: String
    ): updateinfResponseRemote {  // Предполагается, что это тип возврата
        return try {
            transaction {
                User.update({ User.id eq user_id }) {
                    it[User.firstName] = first_name
                    it[User.lastName] = last_name
                    it[User.group] = group
                }
            }
            updateinfResponseRemote(infUpdated = true)  // Явный возврат
        } catch (e: Exception) {
            e.printStackTrace()
            updateinfResponseRemote(infUpdated = false)  // Явный возврат
        }
    }
}