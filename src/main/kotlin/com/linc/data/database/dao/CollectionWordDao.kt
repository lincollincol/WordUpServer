package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class CollectionWordDao {

    suspend fun insertCollectionWord(
        collectionId: UUID,
        wordId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundCollectionWord = CollectionWordTable.select {
            (CollectionWordTable.wordId eq wordId) and (CollectionWordTable.collectionId eq collectionId)
        }
            .limit(1)
            .firstOrNull()
        if(foundCollectionWord != null) {
            return@executeQuery foundCollectionWord[CollectionWordTable.id]
        }
        CollectionWordTable.insertIgnore { table ->
            table[CollectionWordTable.id] = UUID.randomUUID()
            table[CollectionWordTable.wordId] = wordId
            table[CollectionWordTable.collectionId] = collectionId
        } get CollectionWordTable.id
    }

}