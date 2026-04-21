package PolosServ.Database.Tokens
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    private val id = Tokens.integer("id").autoIncrement()
    private val token = Tokens.text("token")
    private val user_id = Tokens.integer("user_id")
    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokenDTO.id
                it[token] = tokenDTO.token
                it[user_id] = tokenDTO.user_id
            }
        }
    }
    fun fetchUser(id: Int): TokenDTO {
        val tokens = Tokens.select{ Tokens.id.eq(id) }.single()
        return TokenDTO(
            id = tokens[Tokens.id],
            token = tokens[Tokens.token],
            user_id = tokens[Tokens.user_id],

        )
    }
}