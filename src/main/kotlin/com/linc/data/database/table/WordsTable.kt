package com.linc.data.database.table

import com.linc.data.database.table.CollectionsTable.primaryKey
import com.linc.data.database.table.CollectionsTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object WordsTable : Table("words") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val text: Column<String> = text("text").uniqueIndex()
}