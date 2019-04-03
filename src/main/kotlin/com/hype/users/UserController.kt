package com.hype.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping
    fun all(): List<User> = userService.all()
}