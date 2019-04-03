package com.hype

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Initializer {

    @Autowired
    private lateinit var db: HypeDatabase

    @PostConstruct
    fun initialize() {
        db.connect
    }
}