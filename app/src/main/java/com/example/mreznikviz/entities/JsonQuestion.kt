package com.example.mreznikviz.entities

import java.io.Serializable

data class JsonQuestion (
    var id:Long?,
    var answer:String?,
    var question: String?,
    var value:Long?,
    var airdate:String?,
    var category_id:Long?,
    var game_id:Long?,
    var invalid_count:Long?
):Serializable