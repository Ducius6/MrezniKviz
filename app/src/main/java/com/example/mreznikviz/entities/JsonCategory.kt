package com.example.mreznikviz.entities

import java.io.Serializable

data class JsonCategory (
    var id : String? = null,
    var title : String? = null,
    var clues_count : Long? = null,
    var clues : List<Question>? = null
) : Serializable