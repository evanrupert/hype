package com.hype.transactions

import com.hype.ApiTest
import com.hype.utility.decimal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import java.time.LocalDateTime


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerTest : ApiTest() {
    @Test
    fun `create will add a new transaction to the database`() {
        val transaction = Transaction(
            itemId = 10,
            userId = 12,
            itemState = "sold",
            purchaseDatetime = LocalDateTime.of(2019, 2, 2, 2, 2, 2),
            purchaseAmount = decimal(20.0),
            saleDatetime = LocalDateTime.of(2019, 3, 3, 3, 3, 3),
            saleAmount = decimal(30.0)
        )

        assertPost("/transactions", transaction) { resp: Transaction ->
            expectThat(resp).isEqualTo(transaction)
        }

        val transactions = TransactionRepo.all()

        expectThat(transactions) {
            hasSize(1)
            contains(transaction)
        }
    }

    @Nested
    inner class HasExistingTransactions {
        private val transaction1 = Transaction(
            itemId = 10,
            userId = 12,
            itemState = "sold",
            purchaseDatetime = LocalDateTime.of(2019, 2, 2, 2, 2, 2),
            purchaseAmount = decimal(20.0),
            saleDatetime = LocalDateTime.of(2019, 3, 3, 3, 3, 3),
            saleAmount = decimal(30.0)
        )
        private val transaction2 = Transaction(
            itemId = 20,
            userId = 22,
            itemState = "inInventory",
            purchaseDatetime = LocalDateTime.of(2020, 2, 2, 2, 2, 2),
            purchaseAmount = decimal(40.0),
            saleDatetime = LocalDateTime.of(2020, 3, 3, 3, 3, 3),
            saleAmount = decimal(50.0)
        )

        @BeforeEach
        fun setup() {
            TransactionRepo.create(transaction1)
            TransactionRepo.create(transaction2)
        }

        @Test
        fun `all returns all transactions in the database`() {
            assertGet("/transactions") { resp: List<Transaction> ->
                expectThat(resp) {
                    hasSize(2)
                    contains(transaction1, transaction2)
                }
            }
        }

        @Test
        fun `find returns a specific transaction by id`() {
            assertGet("/transactions/${transaction1.id}") { resp: Transaction ->
                expectThat(resp).isEqualTo(transaction1)
            }
        }

        @Test
        fun `update updates a specific user`() {
            val updatedTransaction = transaction1.copy(
                itemId = 20,
                userId = 22,
                itemState = "inInventory",
                purchaseDatetime = LocalDateTime.of(2020, 2, 2, 2, 2, 2),
                purchaseAmount = decimal(10.0),
                saleDatetime = LocalDateTime.of(2020, 3, 3, 3, 3, 3),
                saleAmount = decimal(40.0)
            )

            assertPut("/transactions", updatedTransaction) { resp: Transaction ->
                expectThat(resp).isEqualTo(updatedTransaction)
            }

            expectThat(TransactionRepo.all()) {
                hasSize(2)
                contains(updatedTransaction)
                doesNotContain(transaction1)
            }
        }

        @Test
        fun `delete removes a transaction from the database`() {
            assertDelete("/transactions/${transaction1.id}") { resp: Transaction ->
                expectThat(resp).isEqualTo(transaction1)
            }

            expectThat(TransactionRepo.all()) {
                hasSize(1)
                doesNotContain(transaction1)
            }
        }
    }
}
