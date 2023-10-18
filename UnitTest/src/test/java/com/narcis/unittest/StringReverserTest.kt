package com.narcis.unittest

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class StringReverserTest {
    lateinit var SUT: StringReverser

    @Before
    fun setUp() {
        SUT = StringReverser()
    }

    @Test
    fun `revers emptyString emptyStringReturned`() {
        val result = SUT.reverse("")
        assertThat(result, `is`(""))
    }

    @Test
    fun `reverse singleCharacter sameStringReturned`(){
        val result = SUT.reverse("a")
        assertThat(result, `is`("a"))
    }

    @Test
    fun `reverse longString reversedStringReturned`() {
        val result = SUT.reverse("she is going to germany or amsterdam for work")
        assertThat(result, `is`("krow rof madretsma ro ynamreg ot gniog si ehs"))
    }
}