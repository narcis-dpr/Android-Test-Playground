package com.narcis.androidunittest.example12
import android.content.Context
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StringRetrieverTest {
    lateinit var SUT : StringRetriever
    val ID = 10
    val STRING = "string"

    @Mock
    var context: Context ?= null
    @Before
    fun setup(){
        SUT = StringRetriever(context!!)
    }

    @Test
    fun `getString correctParameter passedToContext`() {
       SUT.getString(ID)
       verify(context)?.getString(ID)
    }

    @Test
    fun `getString correctResultReturned`() {
    `when`(context?.getString(ID)).thenReturn(STRING)
    val result = SUT.getString(ID)
    assertThat(result, `is`(STRING))
    }
}