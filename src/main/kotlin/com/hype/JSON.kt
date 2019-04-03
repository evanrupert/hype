package com.hype

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

object JSON {
    val mapper = jacksonObjectMapper()

    inline fun<reified T: Any> read(str: String): T =
        mapper.readValue(str)

    fun write(value: Any): String = mapper.writeValueAsString(value)
}