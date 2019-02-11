
package rate.limiter

import io.javalin.Javalin

fun main(args: Array<String>) {
    val rateLimiter: RateLimiter = IntervallRateLimiter(10, 100)

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
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
                ctx.result("Rate limit exceeded")
            }
        }
    }
}