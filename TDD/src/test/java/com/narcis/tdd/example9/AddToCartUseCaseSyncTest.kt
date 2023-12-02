package com.narcis.tdd.example9

import android.accounts.NetworkErrorException
import com.narcis.tdd.example9.AddToCartUseCaseSync.UseCaseResult
import com.narcis.tdd.example9.networking.AddToCartHttpEndpointSync
import com.narcis.tdd.example9.networking.AddToCartHttpEndpointSync.EndpointResult.*
import com.narcis.tdd.example9.networking.CartItemScheme
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddToCartUseCaseSyncTest {
    // endregion constants -------------------------------------------------------------------------
    // region helper fields ------------------------------------------------------------------------
    @Mock
    var mAddToCartHttpEndpointSyncMock: AddToCartHttpEndpointSync? = null

    // endregion helper fields ---------------------------------------------------------------------
    var SUT: AddToCartUseCaseSync? = null
    @Before
    @Throws(Exception::class)
    fun setup() {
        SUT = AddToCartUseCaseSync(mAddToCartHttpEndpointSyncMock!!)
        success()
    }

    @Test
    @Throws(Exception::class)
    fun `addToCartSync correctParametersPassedToEndpoint`() {
        // Arrange
        val ac = ArgumentCaptor.forClass(CartItemScheme::class.java)
        // Act
        SUT!!.addToCartSync(OFFER_ID, AMOUNT)
        // Assert
        verify(mAddToCartHttpEndpointSyncMock)?.addToCartSync(ac.capture())
        assertThat(ac.value.offerId, `is`(OFFER_ID))
        assertThat(ac.value.amount, `is`(AMOUNT))
    }

    @Test
    @Throws(Exception::class)
    fun `addToCartSync success successReturned`() {
        // Arrange
        // Act
        val result = SUT!!.addToCartSync(OFFER_ID, AMOUNT)
        // Assert
        assertThat(result, `is`(UseCaseResult.SUCCESS))
    }

    @Test
    @Throws(Exception::class)
    fun `addToCartSync authError failureReturned`() {
        // Arrange
        authError()
        // Act
        val result = SUT!!.addToCartSync(OFFER_ID, AMOUNT)
        // Assert
        assertThat(result, `is`(UseCaseResult.FAILURE))
    }

    @Test
    @Throws(Exception::class)
    fun `addToCartSync generalError failureReturned`() {
        // Arrange
        generalError()
        // Act
        val result = SUT!!.addToCartSync(OFFER_ID, AMOUNT)
        // Assert
        assertThat(result, `is`(UseCaseResult.FAILURE))
    }

    @Test
    @Throws(Exception::class)
    fun `addToCartSync networkError networkErrorReturned`() {
        // Arrange
        networkError()
        // Act
        val result = SUT!!.addToCartSync(OFFER_ID, AMOUNT)
        // Assert
        assertThat(result, `is`(UseCaseResult.NETWORK_ERROR))
    }

    // region helper methods -----------------------------------------------------------------------
    @Throws(NetworkErrorException::class)
    private fun success() {
        `when`(
            mAddToCartHttpEndpointSyncMock!!.addToCartSync(
                any(
                    CartItemScheme::class.java
                )
            )
        )
            .thenReturn(SUCCESS)
    }

    @Throws(NetworkErrorException::class)
    private fun authError() {
        `when`(
            mAddToCartHttpEndpointSyncMock!!.addToCartSync(
                any(
                    CartItemScheme::class.java
                )
            )
        )
            .thenReturn(AUTH_ERROR)
    }

    @Throws(NetworkErrorException::class)
    private fun generalError() {
        `when`(
            mAddToCartHttpEndpointSyncMock!!.addToCartSync(
                any(
                    CartItemScheme::class.java
                )
            )
        )
            .thenReturn(GENERAL_ERROR)
    }

    @Throws(NetworkErrorException::class)
    private fun networkError() {
        `when`(
            mAddToCartHttpEndpointSyncMock!!.addToCartSync(
                any(
                    CartItemScheme::class.java
                )
            )
        )
            .thenThrow(NetworkErrorException())
    } // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------
    // endregion helper classes --------------------------------------------------------------------
    companion object {
        // region constants ----------------------------------------------------------------------------
        const val OFFER_ID = "offerId"
        const val AMOUNT = 4
    }
}