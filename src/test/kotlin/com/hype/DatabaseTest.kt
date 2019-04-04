package com.hype

import com.hype.items.ItemRepo
import com.hype.transactions.TransactionRepo
import com.hype.users.UserRepo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

open class DatabaseTest {
    @BeforeEach
    @AfterEach
    fun purge() {
        UserRepo.deleteAll()
        ItemRepo.deleteAll()
        TransactionRepo.deleteAll()
    }
}
