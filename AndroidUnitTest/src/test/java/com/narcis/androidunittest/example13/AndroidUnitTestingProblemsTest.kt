package com.narcis.androidunittest.example13

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class AndroidUnitTestingProblemsTest  {
    lateinit var SUT : AndroidUnitTestingProblems

    @Before
    fun setup() {
        SUT = AndroidUnitTestingProblems()
    }

    @Test
    fun `testStaticApiCall`() {
        SUT.callStaticAndroidApi("")
        assertThat(true, `is`(true))
    }
}