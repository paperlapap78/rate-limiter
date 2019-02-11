package rate.limiter

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class HostsLimiter(private val capacity: Int, private val interval: Long) {

    private val level: AtomicInteger = AtomicInteger(capacity)
    private var refillJob: Job

    init {
        refillJob = GlobalScope.launch {
            refill()
        }
    }

    fun consume(): Boolean {
        return level.getAndDecrement() > 0
    }

    private suspend fun refill() {
        while(true) {
            delay(interval)
            level.set(capacity)
        }
    }

}
