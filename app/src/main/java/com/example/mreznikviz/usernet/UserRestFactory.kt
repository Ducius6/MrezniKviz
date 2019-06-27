package com.example.mreznikviz.usernet

import com.example.mreznikviz.usernet.userretrofit.UserRestRetrofit

object UserRestFactory {

    val BASE_IP = "192.168.0.41"

    val instance: UserRestInterface
        get() = UserRestRetrofit()

}