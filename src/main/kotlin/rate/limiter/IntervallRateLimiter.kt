package rate.limiter

import java.util.concurrent.ConcurrentHashMap

class IntervallRateLimiter(private val capacity: Int, private val interval: Long): RateLimiter{

    private val hosts: ConcurrentHashMap<String, HostsLimiter> = ConcurrentHashMap()

    override fun consume(host: String): Boolean {
        hosts.putIfAbsent(host, HostsLimiter(capacity, interval))
        return hosts[host]?.consume() ?: false
    }
}