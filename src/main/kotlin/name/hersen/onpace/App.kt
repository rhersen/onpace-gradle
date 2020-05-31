package name.hersen.onpace

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.plugin.json.JavalinJackson
import khttp.responses.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
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
                val response: Response = khttp.get(
                    "https://www.strava.com/api/v3/athletes/13317026/stats",
                    headers = mapOf("Authorization" to "Bearer ${System.getenv("BEARER")}")
                )
                val mapper = ObjectMapper().registerModule(KotlinModule())
                JavalinJackson.configure(mapper)
                val rootObject: RootObject = mapper.readValue(response.text, RootObject::class.java)
                val distance: Double = rootObject.ytd_run_totals.distance
                val target: Double = 15e5 * LocalDate.now().dayOfYear / 366.0
                ctx.html(
                    """<html>
<meta charset='UTF-8'>
<div>Du har sprungit ${String.format("%.1f", distance / 100)} km i år.</div>
<div>Målet är ${String.format("%.1f", target / 100)} km.</div>
<div>Du ligger ${String.format("%.1f", target - distance)} meter efter.</div>"""
                )
            }
        }

        return app
    }
}
