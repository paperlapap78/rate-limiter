package rate.limiter

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class BucketTokenRateLimiter(val capacity: Int, val interval: Long) : RateLimiter {

    private val level: AtomicInteger = AtomicInteger(capacity)
    private var refillJob: Job

    init {
        refillJob = GlobalScope.launch {
            refill()
        }
    }

    override fun consume(): Boolean {
        return level.getAndDecrement() > 0
    }

    suspend fun refill() {
        while(true) {
            delay(interval)
            level.set(capacity)
        }
    }

}
