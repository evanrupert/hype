package com.hype

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiTest : DatabaseTest() {
    @Autowired
    lateinit var mvc: MockMvc

    final inline fun <reified T: Any> assertGet(url: String, crossinline assert: (resp: T) -> Unit) {
        mvc.perform(get(url))
            .andExpect(status().isOk)
            .andExpect {
                val resp = JSON.read<T>(it.response.contentAsString)
                assert(resp)
            }
    }

    final inline fun <reified T: Any> assertPost(url: String, body: Any, crossinline assert: (resp: T) -> Unit) {
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.write(body)))
            .andExpect(status().isOk)
            .andExpect {
                val resp = JSON.read<T>(it.response.contentAsString)
                assert(resp)
            }
    }

    final inline fun <reified T: Any> assertPut(url: String, body: Any, crossinline assert: (resp: T) -> Unit) {
        mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSON.write(body)))
            .andExpect(status().isOk)
            .andExpect {
                val resp = JSON.read<T>(it.response.contentAsString)
                assert(resp)
            }
    }

    final inline fun <reified T: Any> assertDelete(url: String, crossinline assert: (resp: T) -> Unit) {
        mvc.perform(delete(url))
            .andExpect(status().isOk)
            .andExpect {
                val resp = JSON.read<T>(it.response.contentAsString)
                assert(resp)
            }
    }
}
