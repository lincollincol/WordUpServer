package com.linc.data.mapper

import com.linc.data.database.model.UserDatabaseModel
import com.linc.data.database.table.UsersTable
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

fun ResultRow.toDbModel() = UserDatabaseModel(
    id = get(UsersTable.id),
    credentialsId = get(UsersTable.credentialsId),
    name = get(UsersTable.name)
)

fun UserDatabaseModel.toApiModel() = UserApiModel.Response(
    id = id.toString(),
    name = name
)