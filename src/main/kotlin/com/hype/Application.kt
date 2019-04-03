package com.hype

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HypeApplication

// TODO: Connect to test database when running tests
// TODO: Finish CRUD for users/items/transactions
// TODO: Implement flyway in order to handle database migrations

fun main(args: Array<String>) {
    HypeDatabase.connect
    runApplication<HypeApplication>(*args)
}
