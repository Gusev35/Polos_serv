package PolosServ.Database.Subjects
import PolosServ.Database.Progress.User_Progress
import PolosServ.Database.Teacher.Teacher
import PolosServ.Database.level.Level
import PolosServ.features.Disciplines.DisciplineDTO
import PolosServ.Database.users.User
import PolosServ.Database.users.UserDTO
import PolosServ.features.levelsState.LvlStateResponseRemote
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Subject : Table("subject") {
    val id = integer("id").autoIncrement()
    val name = text("name")
    val description = text("description").nullable()


    fun insert(progressDTO: SubDTO)
    {
        transaction {
            Subject.insert{
                it[id] = progressDTO.id
                it[name] = name
                it[description] = description
            }
        }
    }
    fun fetchAllDisciplines(): List<DisciplineDTO> {
        return transaction {
            selectAll().map {
                DisciplineDTO(
                    id = it[Subject.id],
                    name = it[name],
                    description = it[description]
                )
            }
        }
    }
    fun LvlState(subjectId: Int, userId: Int): List<LvlStateResponseRemote> {
        return try {
            transaction {
                Level
                    .join(User_Progress, JoinType.INNER, additionalConstraint = {
                        Level.id eq User_Progress.level_id
                    })
                    .select {
                        (Level.subject_id eq subjectId) and
                                (User_Progress.user_id eq userId)
                    }
                    .map { row ->
                        LvlStateResponseRemote(
                            user_id = row[User_Progress.user_id],
                            level_id = row[Level.id],
                            is_correct = row[User_Progress.is_correct]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}