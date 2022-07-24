package com.linc.data.database.model.word

import java.util.*

data class WordDbModel(
    val wordId: UUID,
    val word: String,
    val translate: List<String>
)