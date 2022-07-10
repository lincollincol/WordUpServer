package com.linc.data.database.table

import com.linc.data.database.table.UsersTable.primaryKey
import com.linc.data.database.table.UsersTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object CollectionsTable : Table("collections") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val name: Column<String> = text("name")
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
}