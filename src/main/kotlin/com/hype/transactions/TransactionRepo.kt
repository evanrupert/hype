package com.hype.transactions

import com.hype.Repo
import com.hype.utility.convertToDateTime
import com.hype.utility.convertToLocalDateTime
import org.jetbrains.exposed.sql.*
import java.util.*

object TransactionRepo : Repo() {

    object Transactions : Table("transactions") {
        val id = uuid("id")
        val itemId = integer("itemid")
        val userId = integer("userid")
        val itemState = varchar("itemstate", 255)
        val purchaseDatetime = datetime("purchasedatetime")
        val purchaseAmount = decimal("purchaseamount", 2, 2)
        val saleDatetime = datetime("saledatetime")
        val saleAmount = decimal("saleamount", 2, 2)

        fun toTransaction(row: ResultRow): Transaction =
            Transaction(
                id = row[Transactions.id],
                itemId = row[Transactions.itemId],
                userId = row[Transactions.userId],
                itemState = row[Transactions.itemState],
                purchaseDatetime = convertToLocalDateTime(row[Transactions.purchaseDatetime]),
                purchaseAmount = row[Transactions.purchaseAmount],
                saleDatetime = convertToLocalDateTime(row[Transactions.saleDatetime]),
                saleAmount = row[Transactions.saleAmount]
            )
    }

    fun all(): List<Transaction> = query {
        Transactions.selectAll().map(Transactions::toTransaction)
    }

    fun find(id: UUID): Transaction = query {
        Transactions.select { Transactions.id eq id }
            .map(Transactions::toTransaction)
            .first()
    }

    fun create(transaction: Transaction) = query {
        Transactions.insert {
            it[id] = transaction.id
            it[itemId] = transaction.itemId
            it[userId] = transaction.userId
            it[itemState] = transaction.itemState
            it[purchaseDatetime] = convertToDateTime(transaction.purchaseDatetime)
            it[purchaseAmount] = transaction.purchaseAmount
            it[saleDatetime] = convertToDateTime(transaction.saleDatetime)
            it[saleAmount] = transaction.saleAmount
        }
    }

    fun update(transaction: Transaction) = query {
        Transactions.update({ Transactions.id eq transaction.id }) {
            it[itemId] = transaction.itemId
            it[userId] = transaction.userId
            it[itemState] = transaction.itemState
            it[purchaseDatetime] = convertToDateTime(transaction.purchaseDatetime)
            it[purchaseAmount] = transaction.purchaseAmount
            it[saleDatetime] = convertToDateTime(transaction.saleDatetime)
            it[saleAmount] = transaction.saleAmount
        }
    }

    fun delete(id: UUID) = query {
        Transactions.deleteWhere { Transactions.id eq id }
    }

    fun deleteAll() = query {
        Transactions.deleteAll()
    }
}
