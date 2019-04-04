package com.hype.transactions

import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionService {
    fun all(): List<Transaction> = TransactionRepo.all()

    fun find(id: UUID): Transaction = TransactionRepo.find(id)

    fun create(transaction: Transaction): Transaction {
        TransactionRepo.create(transaction)
        return TransactionRepo.find(transaction.id)
    }

    fun update(transaction: Transaction): Transaction {
        TransactionRepo.update(transaction)
        return TransactionRepo.find(transaction.id)
    }

    fun delete(id: UUID): Transaction {
        val transaction = TransactionRepo.find(id)
        TransactionRepo.delete(id)
        return transaction
    }
}

