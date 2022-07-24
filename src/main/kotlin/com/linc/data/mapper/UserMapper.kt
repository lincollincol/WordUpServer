package com.linc.data.mapper

import com.linc.data.database.model.user.UserDbModel
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserDbModel() = UserDbModel(
    id = get(UsersTable.id),
    name = get(UsersTable.name)
)

fun UserDbModel.toApiModel() = UserApiModel(
    id = id.toString(),
    name = name
)