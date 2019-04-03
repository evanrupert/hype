package com.hype.users

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
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