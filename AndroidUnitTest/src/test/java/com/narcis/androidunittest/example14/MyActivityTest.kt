package com.narcis.androidunittest.example14

import org.junit.Before
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.*

class MyActivityTest {
    lateinit var SUT: MyActivity
    @Before
    fun setup() {
    SUT =  MyActivity()

    }

    @Test
    fun `onStart increamentsCountByOne`() {
        SUT.onStart()
        val result = SUT.count
        assertThat(result, `is`(1))
    }
}