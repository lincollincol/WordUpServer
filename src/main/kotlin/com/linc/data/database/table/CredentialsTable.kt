package com.linc.data.database.table

import com.linc.data.database.table.UsersTable.primaryKey
import com.linc.data.database.table.UsersTable.references
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object CredentialsTable : Table("credentials") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val password: Column<String> = text("password")
    val login: Column<String> = text("login").uniqueIndex()
}