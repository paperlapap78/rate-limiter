package rate.limiter

interface RateLimiter {
    fun consume(host: String): Boolean
}