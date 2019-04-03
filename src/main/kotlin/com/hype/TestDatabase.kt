package com.hype

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class TestDatabase : HypeDatabase {
    override val connect by lazy {
        println("Connecting to TEST database")

        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val dbUser = System.getenv("HYPE_DATABASE_USER") ?: ""
        val dbPassword = System.getenv("HYPE_DATABASE_PASSWORD") ?: ""

        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/hype_test"
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}
