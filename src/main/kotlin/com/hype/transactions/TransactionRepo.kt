package com.hype.transactions

import com.hype.Repo
import com.hype.utility.convertToDateTime
import org.jetbrains.exposed.sql.*
import java.util.*

object TransactionRepo : Repo() {
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
