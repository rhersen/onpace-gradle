package name.hersen.onpace

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.plugin.json.JavalinJackson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main() {
    JavalinApp(7000).init()
}

class JavalinApp(private val port: Int) {

    fun init(): Javalin {

        // get heroku port or uses default (for local environment)
        val port: Int = System.getenv("PORT")?.toIntOrNull() ?: port

        // starts Javalin
        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.result("Not found") }
        }.start(port)

        // app endpoints
        app.routes {
            get("/") { ctx ->
                try {
                    ctx.result(athlete().ytd_run_totals.distance.toString())
                } catch (e: Exception) {
                    ctx.status(400).result(e.message ?: e.toString())
                }

            }
        }

        return app
    }

    private fun athlete(): RootObject {
        val url = URL("https://www.strava.com/api/v3/athletes/13317026/stats")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            con.setRequestProperty("Authorization", "Bearer " + System.getenv("BEARER"))
            val content = StringBuilder()
            BufferedReader(InputStreamReader(con.inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    content.append(line)
                    line = reader.readLine()
                }
                val mapper = ObjectMapper().registerModule(KotlinModule())
                JavalinJackson.configure(mapper)
                return mapper.readValue(content.toString(), RootObject::class.java)
            }
        } finally {
            println(con.responseCode)
        }
    }
}
