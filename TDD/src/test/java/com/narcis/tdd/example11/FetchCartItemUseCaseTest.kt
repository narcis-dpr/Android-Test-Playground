package com.narcis.tdd.example11

import com.narcis.tdd.example11.FetchCartItemsUseCase.*
import com.narcis.tdd.example11.cart.CartItem
import com.narcis.tdd.example11.networking.CartItemSchema
import com.narcis.tdd.example11.networking.GetCartItemsHttpEndpoint
import com.narcis.tdd.example11.networking.GetCartItemsHttpEndpoint.Callback
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchCartItemUseCaseTest {
    private val LIMIT = 10
    val PRICE = 5
    val DESCRIPTION = "description"
    val TITLE = "title"
    val ID = "id"
    lateinit var SUT : FetchCartItemsUseCase

    @Captor
    var mAcListCartItem: ArgumentCaptor<List<CartItem>>? = null

    @Mock
    var mGetCartItemsHttpEndpointMock: GetCartItemsHttpEndpoint ? = null

    @Mock
    var mListenerMock1: Listener? = null

    @Mock
    var mListenerMock2: Listener? = null
    @Before
    fun setup() {
        SUT = FetchCartItemsUseCase(mGetCartItemsHttpEndpointMock!!)
    }
    private fun getCartItemSchemes(): List<CartItemSchema>? {
        val schemas: MutableList<CartItemSchema> = java.util.ArrayList<CartItemSchema>()
        schemas.add(
            CartItemSchema(
                 ID,
                 TITLE,
                 DESCRIPTION,
                 PRICE
            )
        )
        return schemas
    }
    @Test
    fun `fetchCartItems correctLimitPassedEndPoint`() {
        val acInt = ArgumentCaptor.forClass(Int::class.java)
       SUT.fetchCartItemsAndNotify(LIMIT)
        verify(mGetCartItemsHttpEndpointMock)?.getCartItems(acInt.capture(), any(Callback::class.java))
        assertThat(acInt.value, `is`(LIMIT))
    }

    @Test
    @Throws(Exception::class)
    fun `fetchCartItems success observersNotifiedWithCorrectData`() {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1!!)
        SUT.registerListener(mListenerMock2!!)
        SUT.fetchCartItemsAndNotify( LIMIT)
        // Assert
        verify(mListenerMock1)?.onCartItemsFetched(mAcListCartItem?.capture())
        verify(mListenerMock2)?.onCartItemsFetched(mAcListCartItem?.capture())
        val captures: List<List<CartItem>> = mAcListCartItem?.allValues!!
        val capture1 = captures[0]
        val capture2 = captures[1]
        assertThat(capture1, `is`(getCartItems()))
        assertThat(capture2, `is`(getCartItems()))
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `fetchCartItems success unsubscribedObserversNotNotified`() {
        // Arrange
        // Act
        SUT.registerListener(mListenerMock1!!)
        SUT.registerListener(mListenerMock2!!)
        SUT.unregisterListener(mListenerMock2!!)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1)?.onCartItemsFetched(any(List::class.java) as List<CartItem?>?)
        verifyNoMoreInteractions(mListenerMock2)
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `fetchCartItems generalError observersNotifiedOfFailure`() {
        // Arrange
        generaError()
        // Act
        SUT.registerListener(mListenerMock1!!)
        SUT.registerListener(mListenerMock2!!)
        SUT.fetchCartItemsAndNotify(LIMIT)
        // Assert
        verify(mListenerMock1)?.onFetchCartItemsFailed()
        verify(mListenerMock2)?.onFetchCartItemsFailed()
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun `fetchCartItems networkError observersNotifiedOfFailure`() {
        // Arrange
        networkError()
        // Act
        SUT.registerListener(mListenerMock1!!)
        SUT.registerListener(mListenerMock2!!)
        SUT.fetchCartItemsAndNotify( LIMIT)
        // Assert
        verify(mListenerMock1)?.onFetchCartItemsFailed()
        verify(mListenerMock2)?.onFetchCartItemsFailed()
    }
    // region helper methods -----------------------------------------------------------------------

    // region helper methods -----------------------------------------------------------------------
    private fun getCartItems(): List<CartItem>? {
        val cartItems: MutableList<CartItem> = ArrayList<CartItem>()
        cartItems.add(
            CartItem(
                 ID,
                 TITLE,
                 DESCRIPTION,
                 PRICE
            )
        )
        return cartItems
    }

    private fun success() {
        Mockito.doAnswer { invocation ->
            val args = invocation.arguments
            val callback = args[1] as Callback
            callback.onGetCartItemsSucceeded(getCartItemSchemes())
            null
        }.`when`(mGetCartItemsHttpEndpointMock)?.getCartItems(
            ArgumentMatchers.anyInt(), any(
                Callback::class.java
            )
        )
    }

    private fun networkError() {
        Mockito.doAnswer { invocation ->
            val args = invocation.arguments
            val callback = args[1] as Callback
            callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.NETWORK_ERROR)
            null
        }.`when`(mGetCartItemsHttpEndpointMock)?.getCartItems(
            ArgumentMatchers.anyInt(), any(
                Callback::class.java
            )
        )
    }

    private fun generaError() {
        Mockito.doAnswer { invocation ->
            val args = invocation.arguments
            val callback = args[1] as Callback
            callback.onGetCartItemsFailed(GetCartItemsHttpEndpoint.FailReason.GENERAL_ERROR)
            null
        }.`when`(mGetCartItemsHttpEndpointMock)?.getCartItems(
            ArgumentMatchers.anyInt(), any(
                Callback::class.java
            )
        )
    }

}