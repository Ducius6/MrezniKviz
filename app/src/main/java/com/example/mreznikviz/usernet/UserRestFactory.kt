package com.example.mreznikviz.usernet

import com.example.mreznikviz.usernet.userretrofit.UserRestRetrofit

object UserRestFactory {
    //10.0.2.2
    val BASE_IP = "http://mreznikvizroute-mreznikviz.e4ff.pro-eu-west-1.openshiftapps.com/"

    val instance: UserRestInterface
        get() = UserRestRetrofit()

}