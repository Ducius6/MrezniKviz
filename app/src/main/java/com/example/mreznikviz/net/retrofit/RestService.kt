package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.Quizz
import retrofit.http.GET
import retrofit.http.Path

interface RestService {

    @GET("/category?id={id}")
    fun getQuizzQuestions(@Path("id") categoryId: Long?): Quizz?


}