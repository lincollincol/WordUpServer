package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.model.user.UserDbModel
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.mapper.toUserDbModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class UsersDao {

    suspend fun insertUser(
        name: String,
        credentialsId: UUID
    ): Result<UUID?> = SqlExecutor.executeQuery {
        val foundUser = UsersTable.select { UsersTable.credentialsId eq credentialsId }
            .limit(1)
            .firstOrNull()
        if(foundUser != null) {
            return@executeQuery foundUser[UsersTable.id]
        }
        UsersTable.insertIgnore { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.credentialsId] = credentialsId
            table[UsersTable.name] = name
        } get UsersTable.id
    }

    suspend fun selectUser(id: UUID): Result<UserDbModel?> = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq id }
            .limit(1)
            .firstOrNull()
            ?.toUserDbModel()
    }

    suspend fun selectByCredentials(id: UUID): Result<UserDbModel?> = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .slice(UsersTable.id, UsersTable.name, CredentialsTable.id)
            .select { CredentialsTable.id eq id }
            .limit(1)
            .firstOrNull()
            ?.toUserDbModel()
    }

    /*suspend fun selectUserCr(login: String): Result<UserDbModel?> = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { CredentialsTable.login eq login }
            .limit(1)
            .firstOrNull()
            ?.toUserCredentialsDbModel()
    }*/

    suspend fun selectUsers() = SqlExecutor.executeQuery {
        UsersTable.selectAll().mapNotNull(ResultRow::toUserDbModel)
    }

}