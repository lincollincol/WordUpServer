package com.linc.data.mapper

import com.linc.data.database.model.collection.CollectionDbModel
import com.linc.data.database.table.CollectionWordTable
import com.linc.data.database.table.CollectionsTable
import com.linc.data.network.collection.CollectionApiModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.count

fun ResultRow.toCollectionDbModel() = CollectionDbModel(
    id = get(CollectionsTable.id),
    userId = get(CollectionsTable.userId),
    name = get(CollectionsTable.name),
    wordsCount = get(CollectionWordTable.id.count())
)

fun CollectionDbModel.toCollectionApiModel() = CollectionApiModel(
    id = id.toString(),
    name = name,
    wordsCount = wordsCount
)