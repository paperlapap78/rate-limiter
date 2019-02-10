package rate.limiter

interface RateLimiter {
    fun consume(): Boolean
}