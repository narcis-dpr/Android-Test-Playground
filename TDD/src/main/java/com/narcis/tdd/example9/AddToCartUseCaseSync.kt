package com.narcis.tdd.example9

import android.accounts.NetworkErrorException
import com.narcis.tdd.example9.networking.AddToCartHttpEndpointSync
import com.narcis.tdd.example9.networking.CartItemScheme

class AddToCartUseCaseSync(private val mAddToCartHttpEndpointSync: AddToCartHttpEndpointSync) {
    enum class UseCaseResult {
        SUCCESS, FAILURE, NETWORK_ERROR
    }

    fun addToCartSync(offerId: String?, amount: Int): UseCaseResult {
        val result: AddToCartHttpEndpointSync.EndpointResult = try {
            mAddToCartHttpEndpointSync.addToCartSync(CartItemScheme(offerId, amount))
        } catch (e: NetworkErrorException) {
            return UseCaseResult.NETWORK_ERROR
        }
        return when (result) {
            AddToCartHttpEndpointSync.EndpointResult.SUCCESS -> {
                UseCaseResult.SUCCESS
            }

            AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR -> {
                UseCaseResult.FAILURE
            }

            AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR -> {
                UseCaseResult.FAILURE
            }

            else -> {
                throw RuntimeException("invalid endpoint result: $result")
            }
        }
    }
}