package com.narcis.unittest

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class IntervalsAdjacencyDetectorTest {
    lateinit var SUT : IntervalsAdjacencyDetector

    @Before
    fun setUp() {
       SUT = IntervalsAdjacencyDetector()
    }

    @Test
    fun `isAdjacency interval1BeforeInterval2 falseReturned`() {
        val interval1 = Interval(-1,5)
        val interval2 = Interval(6, 10)
        val result = SUT.isAdjacent(interval1, interval2)
        assertThat(result, `is`(false))

    }
    @Test
    fun `isAdjacency interval1BeforeInterval2 trueReturned`() {
        val interval1 = Interval(-1,5)
        val interval2 = Interval(5, 10)
        val result = SUT.isAdjacent(interval1, interval2)
        assertThat(result, `is`(true))

    }
//    @Test
//    fun `isAdjacency interval1AfterInterval2 falseReturned`() {
//        val interval1 = Interval(-1,5)
//        val interval2 = Interval(-10, -3)
//        val result = SUT.isAdjacent(interval1, interval2)
//        assertThat(result, `is`(false))
//
//    }
    @Test
    fun `isAdjacency interval1AfterInterval2 trueReturned`() {
        val interval1 = Interval(-1,5)
        val interval2 = Interval(-10, -1)
        val result = SUT.isAdjacent(interval1, interval2)
        assertThat(result, `is`(true))

    }
}