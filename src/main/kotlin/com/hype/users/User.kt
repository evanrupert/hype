package com.hype.users

import java.util.*

data class User(
    val email: String,
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val id: UUID = UUID.randomUUID()
)
