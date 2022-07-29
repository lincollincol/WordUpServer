package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.*
import com.linc.data.mapper.toUserWordDbModel
import com.linc.data.mapper.toWordDbModel
import com.linc.data.utils.SqlDefines
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    /*
    inner join words on user_word.word_id = words.id
    inner join user_word_translate on user_word.id = user_word_translate.user_word_id
    inner join translate on translate.id = user_word_translate.translate_id
     */
    suspend fun selectByCollection(
        collectionId: UUID,
        userId: UUID,
        limit: Int,
        offset: Int,
    ) = SqlExecutor.executeQuery {
        UserWordTable
            .innerJoin(WordsTable, { UserWordTable.wordId }, { WordsTable.id })
            .innerJoin(UserWordTranslateTable, { UserWordTranslateTable.userWordId }, { UserWordTable.id })
            .innerJoin(TranslateTable, { TranslateTable.id }, { UserWordTranslateTable.translateId })
            .innerJoin(CollectionWordTable, { CollectionWordTable.wordId }, { UserWordTable.wordId })
            .slice(
                UserWordTable.id,
                WordsTable.text,
                UserWordTable.bookmarked,
                UserWordTable.learned,
                CollectionWordTable.index,
                TranslateTable.text.groupConcat(SqlDefines.STRING_AGG_SEPARATOR)
            )
            .select { CollectionWordTable.collectionId eq collectionId }
            .andWhere { UserWordTable.userId eq userId }
            .groupBy(UserWordTable.id, WordsTable.text, CollectionWordTable.index)
            .limit(limit, offset)
            .map(ResultRow::toUserWordDbModel)
    }

    suspend fun selectByUser(id: UUID) = SqlExecutor.executeQuery {
        UserWordTable
            .innerJoin(WordsTable)
            .innerJoin(UserWordTranslateTable)
            .innerJoin(TranslateTable)
            .slice(
                UserWordTable.id,
                WordsTable.id,
                WordsTable.text,
                UserWordTable.bookmarked,
                UserWordTable.learned,
                TranslateTable.text.groupConcat(SqlDefines.STRING_AGG_SEPARATOR)
            )
            .select { UserWordTable.userId eq id }
            .groupBy(UserWordTable.id, WordsTable.text)
            .map(ResultRow::toUserWordDbModel)
    }

}