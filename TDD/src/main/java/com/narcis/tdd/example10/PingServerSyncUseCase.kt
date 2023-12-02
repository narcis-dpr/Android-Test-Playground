package com.narcis.tdd.example10

import com.narcis.tdd.example10.networking.PingServerHttpEndpointSync

class PingServerSyncUseCase(pingServerHttpEndpointSync: PingServerHttpEndpointSync) {
    private val mPingServerHttpEndpointSync: PingServerHttpEndpointSync = pingServerHttpEndpointSync
    fun pingServerSync(): UseCaseResult {
        val result = mPingServerHttpEndpointSync.pingServerSync()
        return when (result) {
            PingServerHttpEndpointSync.EndpointResult.SUCCESS -> {
                UseCaseResult.SUCCESS
            }

            PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR -> {
                UseCaseResult.FAILURE
            }

            PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR -> {
                UseCaseResult.FAILURE
            }
        }
    }

    enum class UseCaseResult {
        SUCCESS,
        FAILURE
    }
}