package com.hype.users

import com.hype.Repo
import org.jetbrains.exposed.sql.*
import java.util.*

object UserRepo : Repo() {

    private object Users : Table("users") {
        val id = uuid("id")
        val email = varchar("email", 255)
        val password = varchar("password", 255)
        val firstName = varchar("firstname", 255)
        val lastName = varchar("lastname", 255)

        fun toUser(row: ResultRow): User =
            User(
                id = row[Users.id],
                email = row[Users.email],
                password = row[Users.password],
                firstName = row[Users.firstName],
                lastName = row[Users.lastName]
            )
    }

    fun all(): List<User> = query {
        Users.selectAll().map(Users::toUser)
    }

    fun find(id: UUID): User = query {
        Users.select { Users.id eq id }.map(Users::toUser).first()
    }

    fun create(user: User) = query {
        Users.insert {
            it[id] = user.id
            it[email] = user.email
            it[password] = user.password
            it[firstName] = user.firstName
            it[lastName] = user.lastName
        }
    }

    fun update(user: User) = query {
        Users.update({ Users.id eq user.id }) {
            it[email] = user.email
            it[password] = user.password
            it[firstName] = user.firstName
            it[lastName] = user.lastName
        }
    }

    fun delete(id: UUID) = query {
        Users.deleteWhere { Users.id eq id }
    }

    fun deleteAll() = query {
        Users.deleteAll()
    }
}