package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.JsonCategory
import retrofit.http.GET
import retrofit.http.Path

interface RestService {

    @GET("/category%3Fid={id}")
    fun getQuizzQuestions(@Path("id") categoryId: Long?): JsonCategory?
}