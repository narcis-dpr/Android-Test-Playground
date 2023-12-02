package com.narcis.tdd.example10

import com.narcis.tdd.example10.networking.PingServerHttpEndpointSync
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PingServerSyncUseCaseTest {
     lateinit var SUT : PingServerSyncUseCase

     @Mock
     var mPingServerHttpEndpointSyncMock: PingServerHttpEndpointSync ?= null
     @Before
     fun setup() {
         SUT = PingServerSyncUseCase(mPingServerHttpEndpointSyncMock!!)
         success()
     }

    @Test
    fun `pingServerSync success successReturned`() {
        val result = SUT.pingServerSync()
        assertThat(result, `is`(PingServerSyncUseCase.UseCaseResult.SUCCESS))
    }

    @Test
    fun `pingServerSync generalError failureReturned`() {
       generalError()
       val result = SUT.pingServerSync()
       assertThat(result, `is`(PingServerSyncUseCase.UseCaseResult.FAILURE))
    }

    @Test
    fun `pingServerSync networkError failureReturned`() {
       networkError()
        val result = SUT.pingServerSync()
        assertThat(result, `is`(PingServerSyncUseCase.UseCaseResult.FAILURE))
    }

    // region helper methods -----------------------------------------------------------------------
    private fun success() {
        Mockito.`when`(mPingServerHttpEndpointSyncMock!!.pingServerSync())
            .thenReturn(PingServerHttpEndpointSync.EndpointResult.SUCCESS)
    }
    private fun networkError() {
        Mockito.`when`(mPingServerHttpEndpointSyncMock!!.pingServerSync())
            .thenReturn(PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR)
    }

    private fun generalError() {
        Mockito.`when`(mPingServerHttpEndpointSyncMock!!.pingServerSync())
            .thenReturn(PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR)
    }
}