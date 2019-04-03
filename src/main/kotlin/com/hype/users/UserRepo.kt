package com.hype.users

import com.hype.Repo
import org.jetbrains.exposed.sql.*
import java.util.*

object UserRepo : Repo() {
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