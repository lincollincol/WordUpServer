package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.model.CredentialsDatabaseModel
import com.linc.data.database.model.UserDatabaseModel
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.insertIgnore
import java.util.*

class CredentialsDao {

    suspend fun insertCredentials(model: UserApiModel.Request) = SqlExecutor.executeQuery {
        CredentialsTable.insertIgnore { table ->
            table[CredentialsTable.id] = UUID.randomUUID()
            table[CredentialsTable.password] = model.password
            table[CredentialsTable.login] = model.login
        } get CredentialsTable.id
    }

}