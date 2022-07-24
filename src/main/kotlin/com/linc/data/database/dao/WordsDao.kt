package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionWordTable
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.WordsTable
import com.linc.data.mapper.toWordDbModel
import io.ktor.http.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class WordsDao {

    suspend fun insertWord(
        word: String,
    ) = SqlExecutor.executeQuery {
        val foundWord = WordsTable.select { WordsTable.text.lowerCase() eq word.lowercase() }
            .limit(1)
            .firstOrNull()
        if(foundWord != null) {
            return@executeQuery foundWord[WordsTable.id]
        }
        WordsTable.insert { table ->
            table[WordsTable.id] = UUID.randomUUID()
            table[WordsTable.text] = word
        } get WordsTable.id
    }

    suspend fun selectByPattern(pattern: String) = SqlExecutor.executeQuery {
        WordsTable.innerJoin(TranslateTable)
            .slice(
                WordsTable.id,
                TranslateTable.id,
                WordsTable.text,
                TranslateTable.text,
            )
            .select { WordsTable.text.regexp(pattern) }
            .map {
                it.toWordDbModel()
            }
    }

    suspend fun selectByCollection(id: UUID) = SqlExecutor.executeQuery {
        WordsTable.innerJoin(TranslateTable)
            .innerJoin(CollectionWordTable)
            .slice(
                WordsTable.id,
                WordsTable.text,
                TranslateTable.text.groupConcat(";")
            )
            .select { CollectionWordTable.collectionId eq id }
            .groupBy(WordsTable.id, WordsTable.text)
            .map(ResultRow::toWordDbModel)
    }

}