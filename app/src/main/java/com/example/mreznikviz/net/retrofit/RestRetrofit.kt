package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.net.RestFactory
import com.example.mreznikviz.net.RestInterface
import retrofit.RestAdapter

class RestRetrofit : RestInterface {

    val service : RestService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP + ":8080/api/"
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()
        service = retrofit.create(RestService::class.java)
    }
}