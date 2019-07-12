package com.example.mreznikviz.usernet.userretrofit

import com.example.mreznikviz.entities.User
import com.example.mreznikviz.usernet.UserRestFactory
import com.example.mreznikviz.usernet.UserRestInterface
import retrofit.RestAdapter
import retrofit.client.Response

class UserRestRetrofit:UserRestInterface {

     val service: UserRestService


    init {
        val baseUrl = UserRestFactory.BASE_IP
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseUrl)
            .build()

        service = retrofit.create(UserRestService::class.java)
    }

    override fun registerUser(user: User):Response {
        return service.registerUser(user)
    }

    override fun findAll(from: Int, size: Int): List<User> {
        return service.findAll(from,size)
    }

    override fun updateUser(score: Long, userName: String): Response {
        return service.updateUser(score,userName)
    }

    override fun loginUser(userName: String, password: String): User {
        return service.loginUser(userName,password)
    }

    override fun sendNotification(token: String, quizId:String): Response {
        return service.sendNotification(token,quizId)
    }


}