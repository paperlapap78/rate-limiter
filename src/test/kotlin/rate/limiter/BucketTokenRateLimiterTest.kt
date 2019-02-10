/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package rate.limiter

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotNull

class BucketTokenRateLimiterTest {
    @Test
    fun testAppHasAGreeting() = runBlocking {
        val classUnderTest = BucketTokenRateLimiter(10, 100)
        for(i in 1..80) {
            delay(5)
            println(classUnderTest.consume())
        }
    }
}
