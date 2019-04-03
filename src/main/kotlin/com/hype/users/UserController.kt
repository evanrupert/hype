package com.hype.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("users")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun all(): List<User> = userService.all()

    @GetMapping("/{id}")
    fun find(@PathVariable id: UUID): User = userService.find(id)

    @PostMapping
    fun create(@RequestBody user: User): User = userService.create(user)

    @PutMapping
    fun update(@RequestBody user: User): User = userService.update(user)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): User = userService.delete(id)
}
