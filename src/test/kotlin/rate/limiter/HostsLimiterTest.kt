package rate.limiter

import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HostsLimiterTest : StringSpec(), TestListener {

    private var hostLimiter: HostsLimiter? = null

    override fun beforeSpec(spec: Spec) {
        hostLimiter = HostsLimiter(10, 100)
    }

    init {
        "consumption within limit is granted" {
            for (i in 1..10) hostLimiter?.consume()?.shouldBeTrue()

        }

        "consumption exceeding limit is refused" {
            for (i in 1..10) hostLimiter?.consume()
            hostLimiter?.consume()?.shouldBeFalse()
        }

        "consumtion after intervall is granted again" {
            for (i in 1..10) hostLimiter?.consume()
            delay(101)
            for (i in 1..10) hostLimiter?.consume()?.shouldBeTrue()
        }

        "consume concurrently" {
            coroutineScope {
                for (i in 1..10) {
                    launch {
                        hostLimiter?.consume()?.shouldBeTrue()
                    }
                }
            }

            delay(10)
            hostLimiter?.consume()?.shouldBeFalse()
        }
    }
}
