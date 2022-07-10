package com.linc.data.database.table

import com.linc.data.database.table.UsersTable.primaryKey
import com.linc.data.database.table.UsersTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserWordTable : Table("user_word") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val learned: Column<Boolean> = bool("learned")
    val bookmarked: Column<Boolean> = bool("bookmarked")
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
    val wordId: Column<UUID> = uuid("word_id").references(WordsTable.id)
}