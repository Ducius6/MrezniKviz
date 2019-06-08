package com.example.mreznikviz.usernet

import com.example.mreznikviz.usernet.userretrofit.UserRestRetrofit

object UserRestFactory {

    val BASE_IP = "10.0.2.2"

    val instance: UserRestInterface
        get() = UserRestRetrofit()

}