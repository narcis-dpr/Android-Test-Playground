package com.narcis.unittest

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class StringDuplicatorTest {
    lateinit var SUT: StringDuplicator

    @Before
    fun setUp() {
       SUT = StringDuplicator()
    }

    @Test
    fun `duplicate string emptyStringReturned`() {
        val result = SUT.duplicate("")
        assertThat(result, `is`(""))
    }

    @Test
    fun `duplicate string doubleTheSameCharacter`() {
        val result = SUT.duplicate("a")
        assertThat(result, `is`("aa"))
    }

    @Test
    fun `duplicate string CompleteStringDuplicated`() {
        val result = SUT.duplicate("abcsdre")
        assertThat(result, `is`("abcsdreabcsdre"))
    }
}