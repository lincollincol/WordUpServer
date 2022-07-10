package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table("users") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val name: Column<String> = text("name")
    val credentialsId: Column<UUID> = uuid("credentials_id").references(CredentialsTable.id)
}
