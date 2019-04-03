package com.hype.items

import java.util.*

data class Item(
    val userId: Int,
    val brand: String = "",
    val model: String = "",
    val size: String = "",

    val id: UUID = UUID.randomUUID()
)

