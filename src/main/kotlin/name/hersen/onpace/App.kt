package name.hersen.onpace

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get

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
                ctx.status(200).result("Hello Javalin with Kotlin 1.3.21 on Heroku!")
            }
        }

        return app
    }
}
