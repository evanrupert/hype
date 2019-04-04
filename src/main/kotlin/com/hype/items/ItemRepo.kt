package com.hype.items

import com.hype.Repo
import org.jetbrains.exposed.sql.*
import java.util.*

object ItemRepo : Repo() {

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

    fun all(): List<Item> = query {
        Items.selectAll().map(Items::toItem)
    }

    fun find(id: UUID): Item = query {
        Items.select { Items.id eq id }.map(Items::toItem).first()
    }

    fun create(item: Item) = query {
        Items.insert {
            it[id] = item.id
            it[userId] = item.userId
            it[brand] = item.brand
            it[model] = item.model
            it[size] = item.size
        }
    }

    fun update(item: Item) = query {
        Items.update({ Items.id eq item.id }) {
            it[userId] = item.userId
            it[brand] = item.brand
            it[model] = item.model
            it[size] = item.size
        }
    }

    fun delete(id: UUID) = query {
        Items.deleteWhere { Items.id eq id }
    }

    fun deleteAll() = query {
        Items.deleteAll()
    }
}
