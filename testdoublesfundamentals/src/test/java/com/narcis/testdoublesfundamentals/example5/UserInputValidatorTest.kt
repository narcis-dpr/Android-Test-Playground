package com.narcis.testdoublesfundamentals.example5

import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class UserInputValidatorTest {
    lateinit var SUT : UserInputValidator

    @Before
    fun setUp() {
        SUT = UserInputValidator()
    }

    @Test
    fun `isValidFullName validateFullName trueReturned`() {
        val result = SUT.isValidFullName("validFullName")
        assertThat(result, `is`(true))
    }

    @Test
    fun `isValidFullName invalidateFullName falseReturned`() {
        val result = SUT.isValidFullName("")
        assertThat(result, `is`(false))
    }

    @Test
    fun `isValidUsername validUsername trueReturned`() {
        val result = SUT.isValidUsername("validUsername")
        assertThat(result, `is`(true))
    }

    @Test
    fun `isValidUsername invalidUsername falseReturned`() {
        val result = SUT.isValidUsername("")
        assertThat(result, `is`(false))
    }
}