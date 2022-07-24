package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.WordsTable
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.select
import java.util.*

class TranslateDao {

    suspend fun insertTranslate(
        translate: String,
        wordId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundTranslate = TranslateTable
            .select { TranslateTable.text.lowerCase() eq translate.lowercase() }
            .limit(1)
            .firstOrNull()
        if(foundTranslate != null) {
            return@executeQuery foundTranslate[TranslateTable.id]
        }
        TranslateTable.insertIgnore { table ->
            table[TranslateTable.id] = UUID.randomUUID()
            table[TranslateTable.wordId] = wordId
            table[TranslateTable.text] = translate
        } get TranslateTable.id
    }

}