package com.hype.transactions

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class Transaction(
    val itemId: Int,
    val userId: Int,
    val itemState: String = "",
    val purchaseDatetime: LocalDateTime = LocalDateTime.now(),
    val purchaseAmount: BigDecimal = BigDecimal.ZERO.setScale(2),
    val saleDatetime: LocalDateTime = LocalDateTime.now(),
    val saleAmount: BigDecimal = BigDecimal.ZERO.setScale(2),

    val id: UUID = UUID.randomUUID()
)
