package com.narcis.mockito.example7

import com.narcis.mockito.example7.authtoken.AuthTokenCache
import com.narcis.mockito.example7.eventbus.EventBusPoster
import com.narcis.mockito.example7.eventbus.LoggedInEvent
import com.narcis.mockito.example7.networking.LoginHttpEndpointSync
import com.narcis.mockito.example7.networking.NetworkErrorException
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.util.Objects

val USERNAME = "username"
val PASSWORD = "password"
val AUTH_TOKEN = "authToken"

class LoginUseCaseSyncTest {
    lateinit var mLoginHttpEndpointSyncMock: LoginHttpEndpointSync
    lateinit var mAuthTokenCacheMock: AuthTokenCache
    lateinit var mEventBusPosterMock: EventBusPoster

    lateinit var SUT: LoginUseCaseSync

    @Before
    @Throws(Exception::class)
    fun setup() {
        mLoginHttpEndpointSyncMock = mock(LoginHttpEndpointSync::class.java)
        mAuthTokenCacheMock = mock(AuthTokenCache::class.java)
        mEventBusPosterMock = mock(EventBusPoster::class.java)
        SUT = LoginUseCaseSync(mLoginHttpEndpointSyncMock, mAuthTokenCacheMock, mEventBusPosterMock)
        success()
    }

    @Test
    @Throws(Exception::class)
    fun `loginSync success usernameAndPasswordPassedToEndpoint`() {
        val ac: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.loginSync(USERNAME, PASSWORD)
        verify(mLoginHttpEndpointSyncMock, times(1)).loginSync(ac.capture(), ac.capture())
        val captures = ac.allValues
        assertThat(captures[0], `is`(USERNAME))
        assertThat(captures[1], `is`(PASSWORD))
    }

    @Test
    @Throws(Exception::class)
    fun `loginSync success authTokenCached`() {
        val ac: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.loginSync(USERNAME, PASSWORD)
        verify(mAuthTokenCacheMock).cacheAuthToken(ac.capture())
        assertThat(ac.value, `is`(AUTH_TOKEN))
    }

    @Test
    @Throws(Exception::class)
    fun `loginSync generalError authTokenNotCached`() {
        generalError()
        SUT.loginSync(USERNAME, PASSWORD)
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync authError authTokenNotCached`() {
        authError()
        SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync serverError authTokenNotCached`() {
        serverError()
        SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        verifyNoMoreInteractions(mAuthTokenCacheMock)
    }

    @Test
    @Throws(Exception::class)
    fun `loginSync success loggedInEventPosted`() {
        val ac = ArgumentCaptor.forClass(Objects::class.java)
        SUT.loginSync(USERNAME, PASSWORD)
        verify(mEventBusPosterMock).postEvent(ac.capture())
        assertThat(ac.value, `is`(instanceOf(LoggedInEvent::class.java)))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync generalError noInteractionWithEventBusPoster`() {
        generalError()
        SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        verifyNoMoreInteractions(mEventBusPosterMock)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync authError noInteractionWithEventBusPoster`() {
        authError()
        SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        verifyNoMoreInteractions(mEventBusPosterMock)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun loginSync_serverError_noInteractionWithEventBusPoster() {
        serverError()
        SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        verifyNoMoreInteractions(mEventBusPosterMock)
    }
    @Test
    fun `loginSync success successReturned`() {
    val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
    assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.SUCCESS))
    }

    @Test
    fun `loginSync serverError failureReturned`(){
        serverError()
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    fun `loginSync authError failureReturned`(){
        authError()
        val result : LoginUseCaseSync.UseCaseResult = SUT.loginSync(USERNAME, PASSWORD)
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync generalError failureReturned`() {
        generalError()
        val result = SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        Assert.assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.FAILURE))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `loginSync networkError networkErrorReturned`() {
        networkError()
        val result = SUT.loginSync(
            USERNAME,
            PASSWORD
        )
        assertThat(result, `is`(LoginUseCaseSync.UseCaseResult.NETWORK_ERROR))
    }

    @Throws(java.lang.Exception::class)
    private fun generalError() {
        `when`(
            mLoginHttpEndpointSyncMock.loginSync(
                any(String::class.java), any(
                    String::class.java
                )
            )
        )
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,
                    ""
                )
            )
    }

    @Throws(java.lang.Exception::class)
    private fun authError() {
        `when`(
            mLoginHttpEndpointSyncMock.loginSync(
                any(String::class.java), any(
                    String::class.java
                )
            )
        )
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,
                    ""
                )
            )
    }

    @Throws(java.lang.Exception::class)
    private fun serverError() {
        `when`(
            mLoginHttpEndpointSyncMock.loginSync(
                any(String::class.java), any(
                    String::class.java
                )
            )
        )
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,
                    ""
                )
            )
    }
    @Throws(java.lang.Exception::class)
    private fun networkError() {
        doThrow(NetworkErrorException())
            .`when` (mLoginHttpEndpointSyncMock).loginSync(
                any(String::class.java), any(
                    String::class.java
                )
            )
    }

    @Throws(NetworkErrorException::class)
    private fun success() {
        `when`(
            mLoginHttpEndpointSyncMock.loginSync(
                any(String::class.java), any(
                    String::class.java
                )
            )
        )
            .thenReturn(
                LoginHttpEndpointSync.EndpointResult(
                    LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN
                )
            )
    }

}

