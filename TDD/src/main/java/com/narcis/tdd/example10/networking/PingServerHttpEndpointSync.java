package com.narcis.tdd.example10.networking;


public interface PingServerHttpEndpointSync {

    enum EndpointResult {
        SUCCESS,
        GENERAL_ERROR,
        NETWORK_ERROR
    }

    EndpointResult pingServerSync();


}
