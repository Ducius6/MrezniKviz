package com.example.mreznikviz.net.retrofit

import com.example.mreznikviz.entities.User
import retrofit.http.GET
import retrofit.http.PUT
import retrofit.http.Path

interface RestService {

    @PUT("/users/{id}")
    fun createNewUser(@Path("id")personId:Long)

    @GET("/users/{id}")
    fun getUser(@Path("id")personId: Long):User

}