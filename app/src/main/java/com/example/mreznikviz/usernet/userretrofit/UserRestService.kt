package com.example.mreznikviz.usernet.userretrofit

import com.example.mreznikviz.entities.User
import org.springframework.http.ResponseEntity
import retrofit.client.Response
import retrofit.http.*
import java.util.*

interface UserRestService {

    @GET("/users")
    fun findAll(@Query("from")from:Int, @Query("size")size:Int):List<User>

    @POST("/users")
    fun registerUser(@Body user:User):Response

    @PATCH("/users")
    fun updateUser(@Query("score")score:Long, @Query("userName")userName:String):Response

    @GET("/users/login")
    fun loginUser(@Query("userName")userName:String, @Query("password")password:String):User

}