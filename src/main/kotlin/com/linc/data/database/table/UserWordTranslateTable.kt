package com.linc.data.database.table

import com.linc.data.database.table.UsersTable.primaryKey
import com.linc.data.database.table.UsersTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserWordTranslateTable : Table("user_word_translate") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val userWordId: Column<UUID> = uuid("user_word_id").references(UserWordTable.id)
    val translateId: Column<UUID> = uuid("translate_id").references(TranslateTable.id)
}