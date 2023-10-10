package com.emm.betsy

import org.junit.Test

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `check dates`() {
        val savedDateMillis = Instant.now().toEpochMilli()
        val toEpochMilli: Long = LocalDateTime
            .now()
            .minusHours(20)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val savedDate: LocalDate = LocalDate.now().minusDays(0)
        val currentDate: LocalDate = LocalDate.now()
        println(savedDate)
        println(currentDate)
        assert(savedDate.isEqual(currentDate))

    }
}