package com.hype.users

import com.hype.DatabaseTest
import com.hype.JSON
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
class UserControllerTest : DatabaseTest() {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `can create new users`() {
        val bob = User("billybob@email.com", "bobpassword", "Billy", "Bob")

        mvc.perform(post("/users")
                .contentType("application/json")
                .content(JSON.write(bob)))
            .andExpect(status().isOk)
            .andExpect {
                val createResp: User  = JSON.read(it.response.contentAsString)
                expectThat(createResp).isEqualTo(bob)
            }

        val allUsers = UserRepo.all()

        expectThat(allUsers) {
            hasSize(1)
            contains(bob)
        }
    }

    @Nested
    inner class HasExistingUsers {
        private val bob = User("billybob@email.com", "bobpassword", "Billy", "Bob")
        private val john = User("John@johnson.com", "johnpassword", "John", "Johnson")

        @BeforeEach
        fun setup() {
            UserRepo.create(bob)
            UserRepo.create(john)
        }

        @Test
        fun `all returns all users in the database`() {
            mvc.perform(get("/users"))
                .andExpect(status().isOk)
                .andExpect {
                    val users: List<User> = JSON.read(it.response.contentAsString)
                    expectThat(users).contains(bob, john)
                }
        }

        @Test
        fun `find returns a specific user by id`() {
            mvc.perform(get("/users/${bob.id}"))
                .andExpect(status().isOk)
                .andExpect {
                    val user: User = JSON.read(it.response.contentAsString)
                    expectThat(user).isEqualTo(bob)
                }
        }

        @Test
        fun `can update existing users`() {
            val updatedUser = bob.copy(
                email = "new@email.com",
                password = "newpassword",
                firstName = "New",
                lastName = "Name"
            )

            mvc.perform(put("/users")
                    .contentType("application/json")
                    .content(JSON.write(updatedUser)))
                .andExpect(status().isOk)
                .andExpect {
                    val resp: User = JSON.read(it.response.contentAsString)
                    expectThat(resp).isEqualTo(updatedUser)
                }

            val allUsers = UserRepo.all()

            expectThat(allUsers) {
                hasSize(2)
                contains(updatedUser)
                doesNotContain(bob)
            }
        }

        @Test
        fun `delete removes a specific user by id`() {
            mvc.perform(delete("/users/${bob.id}"))
                .andExpect(status().isOk)
                .andExpect {
                    val respUser: User = JSON.read(it.response.contentAsString)
                    expectThat(respUser).isEqualTo(bob)
                }

            val allUsers = UserRepo.all()
            expectThat(allUsers) {
                hasSize(1)
                doesNotContain(bob)
            }
        }
    }
}
