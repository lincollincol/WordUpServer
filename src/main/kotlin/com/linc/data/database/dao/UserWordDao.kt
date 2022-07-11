package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.UserWordTable
import com.linc.data.database.table.WordsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class UserWordDao {

    suspend fun insertUserWord(
        userId: UUID,
        wordId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundUserWord = UserWordTable.select {
            (UserWordTable.userId eq userId) and (UserWordTable.wordId eq wordId)
        }
            .limit(1)
            .firstOrNull()
        if(foundUserWord != null) {
            return@executeQuery foundUserWord[UserWordTable.id]
        }
        UserWordTable.insertIgnore { table ->
            table[UserWordTable.id] = UUID.randomUUID()
            table[UserWordTable.wordId] = wordId
            table[UserWordTable.userId] = userId
            table[UserWordTable.learned] = false
            table[UserWordTable.bookmarked] = false
        } get UserWordTable.id
    }

}