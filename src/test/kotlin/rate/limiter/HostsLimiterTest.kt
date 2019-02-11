package rate.limiter

import io.kotlintest.TestCase
import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HostsLimiterTest : StringSpec(), TestListener {

    private var hostLimiter: HostsLimiter? = null

    override fun beforeTest(testCase: TestCase) {
        hostLimiter = HostsLimiter(10, 200)
    }

    init {
        "consumption within limit is granted" {
            repeat(10) { hostLimiter?.consume()?.shouldBeTrue() }

        }

        "consumption exceeding limit is refused" {
            repeat(10) { hostLimiter?.consume() }
            hostLimiter?.consume()?.shouldBeFalse()
        }

        "consumtion after intervall is granted again" {
            repeat(10) { hostLimiter?.consume() }
            delay(200)
            repeat(10) { hostLimiter?.consume()?.shouldBeTrue() }
        }

        "consume concurrently" {
            coroutineScope {
                repeat(10) {
                    launch {
                        hostLimiter?.consume()?.shouldBeTrue()
                    }
                }
            }

            delay(50)
            hostLimiter?.consume()?.shouldBeFalse()
        }
    }
}
