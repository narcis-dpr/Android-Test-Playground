package com.narcis.testdoublesfundamentals.example6

import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class FitnessTrackerTest {
    lateinit var SUT: FitnessTracker

    @Before
    fun setUp() {
        SUT = FitnessTracker()
    }

    @Test
    fun `step totalIncremented`(){
        SUT.step()
        assertThat(SUT.totalSteps, `is`(1))
    }

    @Test
    fun `runStep totalIncrementedByCorrectRatio`() {
       SUT.runStep()
       assertThat(SUT.totalSteps, `is`(2))
    }
}