package com.hype.users

import com.hype.DatabaseTest
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest : DatabaseTest() {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun `all returns all users in the database`() {
        val user = User(
            email = "test@email.com",
            password = "testpassword",
            firstName = "test",
            lastName = "user"
        )
        UserRepo.create(user)

        mvc.perform(get("/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id", `is`(user.id.toString())))
            .andExpect(jsonPath("$[0].email", `is`(user.email)))
            .andExpect(jsonPath("$[0].password", `is`(user.password)))
            .andExpect(jsonPath("$[0].firstName", `is`(user.firstName)))
            .andExpect(jsonPath("$[0].lastName", `is`(user.lastName)))
    }
}
