package com.narcis.unittest

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class PositiveNumberValidatorTest {
    lateinit var SUT: PositiveNumberValidator

    @Before
    fun setUp() {
        SUT = PositiveNumberValidator()
    }

    @Test
    fun `test negativity`() {
        val res = SUT.isPositive(-1)
        assertThat(res, `is`(false))
    }
    @Test
    fun `test for positive`() {
        val res = SUT.isPositive(0)
        assertThat(res, `is`(false))
    }

    @Test
    fun `test for real positive`() {
        val res = SUT.isPositive(1)
        assertThat(res, `is`(true))
    }
}
