package com.linc.data.mapper

import com.linc.data.database.model.word.UserWordDbModel
import com.linc.data.database.model.word.WordDbModel
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.UserWordTable
import com.linc.data.database.table.WordsTable
import com.linc.data.network.word.UserWordApiModel
import com.linc.data.network.word.WordApiModel
import com.linc.data.utils.SqlDefines
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.groupConcat

fun ResultRow.toWordDbModel() = WordDbModel(
    id = get(WordsTable.id),
    word = get(WordsTable.text),
    translate = get(TranslateTable.text.groupConcat(SqlDefines.STRING_AGG_SEPARATOR))
        .split(SqlDefines.STRING_AGG_SEPARATOR)
)

fun ResultRow.toUserWordDbModel() = UserWordDbModel(
    id = get(UserWordTable.id),
    word = get(WordsTable.text),
    translate = get(TranslateTable.text.groupConcat(SqlDefines.STRING_AGG_SEPARATOR))
        .split(SqlDefines.STRING_AGG_SEPARATOR),
    bookmarked = get(UserWordTable.bookmarked),
    learned = get(UserWordTable.learned),
)

fun WordDbModel.toWordApiModel() = WordApiModel(
    id = id.toString(),
    word = word,
    translate = translate
)

fun UserWordDbModel.toUserWordApiModel() = UserWordApiModel(
    id = id.toString(),
    word = word,
    translate = translate,
    bookmarked = bookmarked,
    learned = learned,
)