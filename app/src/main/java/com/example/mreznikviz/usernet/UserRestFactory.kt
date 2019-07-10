package com.example.mreznikviz.usernet

import com.example.mreznikviz.usernet.userretrofit.UserRestRetrofit

object UserRestFactory {
    //10.0.2.2
    val BASE_IP = "192.168.0.14"

    val instance: UserRestInterface
        get() = UserRestRetrofit()

}