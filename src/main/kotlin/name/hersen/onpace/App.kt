package name.hersen.onpace

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.plugin.json.JavalinJackson
import khttp.responses.Response
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

        val mapper = ObjectMapper().registerModule(KotlinModule())
        JavalinJackson.configure(mapper)

        app.routes {
            get("/login") { ctx ->
                ctx.redirect(
                    "https://www.strava.com/oauth/authorize?client_id=45920&redirect_uri=${URLEncoder.encode(
                        "http://localhost:7000/logged-in",
                        "UTF-8"
                    )}&response_type=code"
                )
            }
            get("/logged-in") { ctx ->
                val code: String = ctx.queryParams("code").joinToString()
                println(System.getenv("CLIENT_ID"))
                val postResponse = khttp.post(
                    "https://www.strava.com/api/v3/oauth/token",
                    params = mapOf(
                        "code" to code,
                        "client_id" to System.getenv("CLIENT_ID"),
                        "client_secret" to System.getenv("CLIENT_SECRET"),
                        "grant_type" to "authorization_code"
                    )
                )
                val authentication: Authentication = mapper.readValue(postResponse.text, Authentication::class.java)
                val getResponse: Response = khttp.get(
                    "https://www.strava.com/api/v3/athletes/13317026/stats",
                    headers = mapOf("Authorization" to "Bearer ${authentication.access_token}")
                )
                if (getResponse.statusCode == 200) {
                    val activityStats: ActivityStats = mapper.readValue(getResponse.text, ActivityStats::class.java)
                    val distance: Double = activityStats.ytd_run_totals.distance
                    val target: Double = 15e5 * LocalDate.now().dayOfYear / 366.0
                    ctx.html(
                        """<html>
    <meta charset='UTF-8'>
    <div>Du har sprungit ${String.format("%.1f", distance * 1e-3)} km i år.</div>
    <div>Målet är ${String.format("%.1f", target * 1e-3)} km.</div>
    <div>Du ligger ${String.format("%.1f", target - distance)} meter efter.</div>"""
                    )
                } else {
                    ctx.status(getResponse.statusCode).result(getResponse.text)
                }
            }
        }

        return app
    }
}
