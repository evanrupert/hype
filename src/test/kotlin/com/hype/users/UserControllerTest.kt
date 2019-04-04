package com.hype.users

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
class UserControllerTest : ApiTest() {

    @Test
    fun `can create new users`() {
        val bob = User("billybob@email.com", "bobpassword", "Billy", "Bob")

        assertPost("/users", bob) { resp: User ->
            expectThat(resp).isEqualTo(bob)
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
            assertGet("/users") { resp: List<User> ->
                expectThat(resp) {
                    hasSize(2)
                    contains(bob, john)
                }
            }
        }

        @Test
        fun `find returns a specific user by id`() {
            assertGet("/users/${bob.id}") { resp: User ->
                expectThat(resp).isEqualTo(bob)
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

            assertPut("/users", updatedUser) { resp: User ->
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
            assertDelete("/users/${bob.id}") { resp: User ->
                expectThat(resp).isEqualTo(bob)
            }

            val allUsers = UserRepo.all()
            expectThat(allUsers) {
                hasSize(1)
                doesNotContain(bob)
            }
        }
    }
}
