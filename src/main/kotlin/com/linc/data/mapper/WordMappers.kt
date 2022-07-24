package com.linc.data.mapper

import com.linc.data.database.model.word.WordDbModel
import com.linc.data.database.table.TranslateTable
import com.linc.data.database.table.WordsTable
import com.linc.data.network.word.WordApiModel
import com.linc.data.utils.SqlDefines
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.groupConcat

fun ResultRow.toWordDbModel() = WordDbModel(
    wordId = get(WordsTable.id),
    word = get(WordsTable.text),
    translate = get(TranslateTable.text.groupConcat(SqlDefines.STRING_AGG_SEPARATOR))
        .split(SqlDefines.STRING_AGG_SEPARATOR)
)


fun WordDbModel.toWordApiModel() = WordApiModel(
    wordId = wordId.toString(),
    word = word,
    translate = translate
)