package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.WordsTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class WordsDao {

    suspend fun insertWord(
        word: String,
    ) = SqlExecutor.executeQuery {
        val foundWord = WordsTable.select { WordsTable.text eq word }
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

}