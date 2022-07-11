package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.UserWordTranslateTable
import com.linc.data.database.table.WordsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class UserWordTranslateDao {

    suspend fun insertUserWordTranslate(
        userWordId: UUID,
        translateId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundUserWordTranslate = UserWordTranslateTable.select {
            (UserWordTranslateTable.userWordId eq userWordId) and (UserWordTranslateTable.translateId eq translateId)
        }
            .limit(1)
            .firstOrNull()
        if(foundUserWordTranslate != null) {
            return@executeQuery foundUserWordTranslate[UserWordTranslateTable.id]
        }
        UserWordTranslateTable.insertIgnore { table ->
            table[UserWordTranslateTable.id] = UUID.randomUUID()
            table[UserWordTranslateTable.userWordId] = userWordId
            table[UserWordTranslateTable.translateId] = translateId
        } get UserWordTranslateTable.id
    }

}