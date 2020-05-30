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
import java.time.LocalDate

fun main() {
    JavalinApp(7000).init()
}

class JavalinApp(private val port: Int) {
    fun init(): Javalin {
        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.result("Not found") }
        }.start(System.getenv("PORT")?.toIntOrNull() ?: this.port)

        app.routes {
            get("/") { ctx ->
                val url = URL("https://www.strava.com/api/v3/athletes/13317026/stats")
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                try {
                    val distance: Double? = athlete(con)?.ytd_run_totals?.distance
                    val target = 15e5 * LocalDate.now().dayOfYear / 366.0
                    ctx.result(distance.toString() + " " + target)
                } catch (e: Exception) {
                    ctx.status(con.responseCode).result(e.message ?: e.toString())
                }
            }
        }

        return app
    }

    private fun athlete(con: HttpURLConnection): RootObject? {
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
    }
}
