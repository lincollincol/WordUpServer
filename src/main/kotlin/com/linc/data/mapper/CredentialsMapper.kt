package com.linc.data.mapper

import com.linc.data.database.model.auth.CredentialsDbModel
import com.linc.data.database.table.CredentialsTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCredentialsDbModel() = CredentialsDbModel(
    id = get(CredentialsTable.id),
    login = get(CredentialsTable.login),
    password = get(CredentialsTable.password),
)