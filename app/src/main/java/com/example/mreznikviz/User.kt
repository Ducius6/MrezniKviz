package com.example.mreznikviz

import java.io.Serializable


data class User (
    var id:Long? = null,
    var name:String? = "",
    var username:String? = "",
    var email:String? = "",
    var points:Long? = 0
):Serializable

