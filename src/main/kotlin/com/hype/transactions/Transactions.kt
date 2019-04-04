package com.hype.transactions

import com.hype.utility.convertToLocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

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
