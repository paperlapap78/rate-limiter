
package rate.limiter

import io.javalin.Javalin

fun main() {

    val capacity = 10
    val interval = 100L


    val rateLimiter: RateLimiter = IntervallRateLimiter(capacity, interval)

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7000)
    app.get("/") { ctx ->
        val host: String? = ctx.host()
        when {
            host == null -> {
                ctx.status(400)
                ctx.result("unaccepted request host")
            }
            rateLimiter.consume(host) -> ctx.result("Hello World")
            else -> {
                ctx.status(429)
                ctx.result("Rate limit exceeded. Try again in ${interval} seconds")
            }
        }
    }
}