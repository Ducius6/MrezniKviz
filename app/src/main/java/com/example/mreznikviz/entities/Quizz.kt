package com.example.mreznikviz.entities

import java.io.Serializable

data class Quizz (
    var id: String,
    var users: List<User>,
    var questions: List<Question>,
    var admin: Long
) : Serializable