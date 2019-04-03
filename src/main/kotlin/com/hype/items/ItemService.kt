package com.hype.items

import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService {
    fun all(): List<Item> = ItemRepo.all()

    fun find(id: UUID): Item = ItemRepo.find(id)

    fun create(item: Item): Item {
        ItemRepo.create(item)
        return ItemRepo.find(item.id)
    }

    fun update(item: Item): Item {
        ItemRepo.update(item)
        return ItemRepo.find(item.id)
    }

    fun delete(id: UUID): Item {
       val item = ItemRepo.find(id)
        ItemRepo.delete(id)
        return item
    }
}