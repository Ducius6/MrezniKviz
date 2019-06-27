package com.example.mreznikviz.usernet

import com.example.mreznikviz.usernet.userretrofit.UserRestRetrofit

object UserRestFactory {
    //192.168.0.14
    val BASE_IP = "10.0.2.2"

    val instance: UserRestInterface
        get() = UserRestRetrofit()

}