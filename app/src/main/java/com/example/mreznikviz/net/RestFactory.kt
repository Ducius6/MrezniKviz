package com.example.mreznikviz.net

import com.example.mreznikviz.net.retrofit.RestRetrofit


object RestFactory {
    val BASE_IP = "10.0.2.2"

    val instance: RestInterface
        get() = RestRetrofit()
}