package com.hype

import com.hype.users.UserRepo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

open class DatabaseTest {
    @BeforeEach
    @AfterEach
    fun purge() {
        UserRepo.deleteAll()
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun connectDB() {
            HypeDatabase.connect
        }
    }
}
