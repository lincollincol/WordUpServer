package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.model.auth.CredentialsDbModel
import com.linc.data.database.table.CredentialsTable
import com.linc.data.mapper.toCredentialsDbModel
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class CredentialsDao {

    suspend fun insertCredentials(
        login: String,
        password: String,
    ) = SqlExecutor.executeQuery {
        val foundCredentials = CredentialsTable.select { CredentialsTable.login eq login }
            .limit(1)
            .firstOrNull()
        if(foundCredentials != null) {
            error("User with this name already exist!")
        }
        CredentialsTable.insertIgnore { table ->
            table[CredentialsTable.id] = UUID.randomUUID()
            table[CredentialsTable.password] = password
            table[CredentialsTable.login] = login
        } get CredentialsTable.id
    }

    suspend fun selectCredentials(
        login: String
    ): Result<CredentialsDbModel?> = SqlExecutor.executeQuery {
        CredentialsTable
            .select { CredentialsTable.login eq login }
            .limit(1)
            .firstOrNull()
            ?.toCredentialsDbModel()
    }
}