package com.example.mreznikviz.net

import com.example.mreznikviz.net.retrofit.RestRetrofit


object RestFactory {
    val BASE_IP = "jservice.io/api/"

    val instance: RestInterface
        get() = RestRetrofit()
}