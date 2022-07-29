package com.linc.data.database.table

import com.linc.data.database.table.CollectionsTable.primaryKey
import com.linc.data.database.table.CollectionsTable.references
import com.linc.data.database.table.TranslateTable.primaryKey
import com.linc.data.database.table.TranslateTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object CollectionWordTable : Table("collection_word") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val index: Column<Int> = integer("index")
    val wordId: Column<UUID> = uuid("word_id").references(WordsTable.id)
    val collectionId: Column<UUID> = uuid("collection_id").references(CollectionsTable.id)
}