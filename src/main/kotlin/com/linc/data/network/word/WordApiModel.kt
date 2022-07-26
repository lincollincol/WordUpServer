package com.linc.data.network.word


data class WordApiModel(
    val id: String,
    val word: String,
    val translate: List<String>
)

