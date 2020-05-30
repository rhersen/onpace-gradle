package name.hersen.onpace

import io.javalin.Javalin
import khttp.get
import khttp.responses.Response
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


class AppTest {

    companion object {

        private lateinit var app : Javalin

        private val url = "http://localhost:7000"

        @BeforeAll @JvmStatic
        fun setUp() {
            app = JavalinApp(7000).init()
        }

        @AfterAll @JvmStatic
        fun tearDown() {
            app.stop()
        }
    }

    @Test
    fun `It should get hello message`() {
        val response : Response = get(url)
        assertEquals(200, response.statusCode)
        assertEquals("Hello Javalin with Kotlin 1.3.72 on Heroku!", response.text)
    }

    @Test
    fun `It should get not found message if resource doesnt exists`() {
        val response : Response = get("$url/error")
        assertEquals(404, response.statusCode)
        assertEquals("Not found", response.text)
    }

}
