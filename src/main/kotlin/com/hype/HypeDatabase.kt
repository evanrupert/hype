package com.hype

import org.jetbrains.exposed.sql.Database

interface HypeDatabase {
    val connect: Database
}
