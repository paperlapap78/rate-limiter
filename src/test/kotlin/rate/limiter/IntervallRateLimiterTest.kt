package rate.limiter

import io.kotlintest.TestCase
import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class IntervallRateLimiterTest: StringSpec(), TestListener {

    private var intervallLimiter: IntervallRateLimiter? = null

    override fun beforeTest(testCase: TestCase) {
        intervallLimiter = IntervallRateLimiter(10, 200)
    }

    init {
        "consumption limit is per host" {
            val hostA = "1.2.3.4"
            val hostB = "0.0.0.0"

            coroutineScope {
                repeat(10) {
                    launch {
                        intervallLimiter?.consume(hostA)?.shouldBeTrue()
                        intervallLimiter?.consume(hostB)?.shouldBeTrue()
                    }
                }
            }

            delay(20)
            intervallLimiter?.consume(hostA)?.shouldBeFalse()
            intervallLimiter?.consume(hostB)?.shouldBeFalse()

            delay(200)
            intervallLimiter?.consume(hostA)?.shouldBeTrue()
            intervallLimiter?.consume(hostB)?.shouldBeTrue()
        }
    }
}