package com.hype.items

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("items")
class ItemController {

    @Autowired
    private lateinit var itemService: ItemService

    @GetMapping
    fun all(): List<Item> = itemService.all()

    @GetMapping("/{id}")
    fun find(@PathVariable id: UUID): Item = itemService.find(id)

    @PostMapping
    fun create(@RequestBody item: Item): Item = itemService.create(item)

    @PutMapping
    fun update(@RequestBody item: Item): Item = itemService.update(item)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): Item = itemService.delete(id)
}
