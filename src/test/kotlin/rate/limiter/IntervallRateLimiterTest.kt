package rate.limiter

import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec


class IntervallRateLimiterTest: StringSpec(), TestListener {

    private var intervallLimiter: IntervallRateLimiter? = null

    override fun beforeSpec(spec: Spec) {
        intervallLimiter = IntervallRateLimiter(10, 100)
    }

    init {
        "" {

        }
    }
}