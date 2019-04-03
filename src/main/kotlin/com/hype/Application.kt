package com.hype

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HypeApplication

fun main(args: Array<String>) {
    HypeDatabase.connect
    runApplication<HypeApplication>(*args)
}
