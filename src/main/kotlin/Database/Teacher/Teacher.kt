package PolosServ.Database.Teacher

import PolosServ.Database.Progress.User_Progress
import PolosServ.Database.users.User
import PolosServ.Database.Subjects.Subject
import PolosServ.Database.level.Level
import PolosServ.features.teacher.teacherResponseRemote
import PolosServ.features.levelsState.LvlStateResponseRemote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Teacher : Table("teacher") {
    val id = integer("id").autoIncrement()
    val user_id = integer("user_id")
    val description_subject = text("description_subject")
    val subject_id = integer("subject_id")

    fun aboutTeacher(subjectId: Int): List<teacherResponseRemote> {
        return try {
            transaction {
                // Получаем всех учителей по subject_id
                Teacher.join(User, JoinType.INNER, Teacher.user_id, User.id)
                    .select { Teacher.subject_id eq subjectId }
                    .map { row ->
                        teacherResponseRemote(
                            userId = row[Teacher.user_id],
                            first_name = row[User.firstName],
                            last_name = row[User.lastName],
                            email_phone = row[User.email]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}