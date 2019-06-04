package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.Quizz
import com.example.mreznikviz.net.RestFactory
import com.example.mreznikviz.net.RestInterface
import retrofit.RestAdapter

class RestRetrofit : RestInterface {

    override fun getQuizzQuestions(quizzId: Long?): Quizz? {
        return service.getQuizzQuestions(quizzId)
    }

    val service : RestService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()
        service = retrofit.create(RestService::class.java)
    }
}