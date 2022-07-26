package com.linc.data.database.model.word

import java.util.*

data class UserWordDbModel(
    val id: UUID,
    val word: String,
    val translate: List<String>,
    val bookmarked: Boolean,
    val learned: Boolean
)