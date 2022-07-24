package com.linc.data.database.model.collection

import java.util.*

data class CollectionDbModel(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val wordsCount: Int
)