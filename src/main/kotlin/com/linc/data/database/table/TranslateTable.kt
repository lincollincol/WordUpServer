package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object TranslateTable : Table("translate") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val text: Column<String> = text("text").uniqueIndex()
    val wordId: Column<UUID> = uuid("word_id").references(WordsTable.id)
}