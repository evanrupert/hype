package com.hype.items

import com.hype.DatabaseTest
import com.hype.JSON
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest : DatabaseTest() {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `create will add a new item to the database`() {
        val item = Item(
            userId = 2,
            brand = "Sperry",
            model = "Shoe",
            size = "Women's 15"
        )

        mvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.write(item)))
            .andExpect(status().isOk)
            .andExpect {
                val resp = JSON.read<Item>(it.response.contentAsString)
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
            mvc.perform(get("/items"))
                .andExpect(status().isOk)
                .andExpect {
                    val items: List<Item> = JSON.read(it.response.contentAsString)
                    expectThat(items) {
                        hasSize(2)
                        contains(nike, sperry)
                    }
                }
        }

        @Test
        fun `find returns a single item by id`() {
            mvc.perform(get("/items/${nike.id}"))
                .andExpect(status().isOk)
                .andExpect {
                    val item: Item = JSON.read(it.response.contentAsString)
                    expectThat(item).isEqualTo(nike)
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

            mvc.perform(put("/items")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JSON.write(updatedItem)))
                .andExpect(status().isOk)
                .andExpect {
                    val resp: Item = JSON.read(it.response.contentAsString)
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
            mvc.perform(delete("/items/${nike.id}"))
                .andExpect(status().isOk)
                .andExpect {
                    val resp: Item = JSON.read(it.response.contentAsString)
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
