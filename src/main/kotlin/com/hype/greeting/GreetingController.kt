package com.hype.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/greeting")
class GreetingController {
    @GetMapping
    fun greet(): String = "Hello, World!"
}
