package com.example.mreznikviz.quiznet

import com.example.mreznikviz.quiznet.quizretrofit.RestRetrofit


object RestFactory {
    val BASE_IP_QUIZ = "jservice.io/api/"

    val instance: RestInterface
        get() = RestRetrofit()
}