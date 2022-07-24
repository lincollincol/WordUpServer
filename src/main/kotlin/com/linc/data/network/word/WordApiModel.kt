package com.linc.data.network.word


data class WordApiModel(
    val wordId: String,
    val word: String,
    val translate: List<String>
)

