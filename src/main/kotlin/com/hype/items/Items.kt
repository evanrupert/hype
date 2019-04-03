package com.hype.items

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object Items : Table("items") {
    val id = uuid("id")
    val userId = integer("userid")
    val brand = varchar("brand", 255)
    val model = varchar("model", 255)
    val size = varchar("size", 255)

    fun toItem(row: ResultRow): Item =
        Item(
            id = row[Items.id],
            userId = row[Items.userId],
            brand = row[Items.brand],
            model = row[Items.model],
            size = row[Items.size]
        )
}

