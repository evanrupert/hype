package com.hype.transactions

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("transactions")
class TransactionController {
    @Autowired
    private lateinit var transactionService: TransactionService

    @GetMapping
    fun all(): List<Transaction> = transactionService.all()

    @GetMapping("/{id}")
    fun find(@PathVariable id: UUID): Transaction = transactionService.find(id)

    @PostMapping
    fun create(@RequestBody transaction: Transaction): Transaction = transactionService.create(transaction)

    @PutMapping
    fun update(@RequestBody transaction: Transaction): Transaction = transactionService.update(transaction)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): Transaction = transactionService.delete(id)
}
