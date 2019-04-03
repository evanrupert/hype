package com.hype.items

import com.hype.Repo
import org.jetbrains.exposed.sql.*
import java.util.*

object ItemRepo : Repo() {
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
