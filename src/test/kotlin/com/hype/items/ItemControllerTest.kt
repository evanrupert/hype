package com.hype.items

import com.hype.ApiTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest : ApiTest() {

    @Test
    fun `create will add a new item to the database`() {
        val item = Item(
            userId = 2,
            brand = "Sperry",
            model = "Shoe",
            size = "Women's 15"
        )

        assertPost("/items", item) { resp: Item ->
            expectThat(resp).isEqualTo(item)
        }

        val items = ItemRepo.all()

        expectThat(items) {
            hasSize(1)
            contains(item)
        }
    }

    @Nested
    inner class HasExistingItems {
        private val nike = Item(
            userId = 1,
            brand = "Nike",
            model = "Air Jordans",
            size = "Men's 10"
        )
        private val sperry = Item(
            userId = 2,
            brand = "Sperry",
            model = "Shoe",
            size = "Women's 15"
        )

        @BeforeEach
        fun setUp() {
            ItemRepo.create(nike)
            ItemRepo.create(sperry)
        }

        @Test
        fun `all returns all items in the database`() {
            assertGet("/items") { resp: List<Item> ->
                expectThat(resp) {
                    hasSize(2)
                    contains(nike, sperry)
                }
            }
        }

        @Test
        fun `find returns a single item by id`() {
            assertGet("/items/${nike.id}") { resp: Item ->
                expectThat(resp).isEqualTo(nike)
            }
        }

        @Test
        fun `update properly updates an item`() {
            val updatedItem = sperry.copy(
                userId = 5,
                brand = "Updated",
                model = "Updated",
                size = "Updated"
            )

            assertPut("/items", updatedItem) { resp: Item ->
                expectThat(resp).isEqualTo(updatedItem)
            }

            expectThat(ItemRepo.all()) {
                hasSize(2)
                contains(updatedItem)
                doesNotContain(sperry)
            }
        }

        @Test
        fun `delete removes a specific item from the database by id`() {
            assertDelete("/items/${nike.id}") { resp: Item ->
                expectThat(resp).isEqualTo(nike)
            }

            expectThat(ItemRepo.all()) {
                hasSize(1)
                contains(sperry)
                doesNotContain(nike)
            }
        }
    }
}
