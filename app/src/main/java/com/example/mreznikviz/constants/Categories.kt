package com.example.mreznikviz.constants

enum class Categories(val id: Long, val title: String) {
    POP_MUSIC(770, "Pop music"),
    SCIENCE(25, "Science"),
    SPORT(6289, "Sport"),
    FOUR_LETTER_WORDS(51, "4 letter words");

    override fun toString(): String {
        return title
    }
}