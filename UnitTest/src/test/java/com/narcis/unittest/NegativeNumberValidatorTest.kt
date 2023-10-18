package com.narcis.unittest

import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class NegativeNumberValidatorTest {
    lateinit var SUT : NegativeNumberValidator

    @Before
    fun setUp() {
        SUT = NegativeNumberValidator()
    }

    @Test
    fun `test negativity`() {
        val res = SUT.isNegative(-1)
        assertThat(res, `is`(true))
    }

    @Test
    fun `test positivity`() {
        val res = SUT.isNegative(0)
        assertThat(res, `is`(false))
    }

    @Test
    fun `test actual positive`() {
        val res = SUT.isNegative(1)
        assertThat(res, `is`(false))
    }
}