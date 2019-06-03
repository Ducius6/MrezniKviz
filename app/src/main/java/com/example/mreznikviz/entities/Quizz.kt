package com.example.mreznikviz.entities

data class Quizz (
    var users: List<User>,
    var questions: List<Question>,
    var admin: Long
)