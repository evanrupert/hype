package com.hype

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction

open class Repo {
    fun <T> query(query: () -> T): T =
        runBlocking {
            transaction {
                query()
            }
        }
}
