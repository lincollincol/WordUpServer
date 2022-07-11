package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.model.UserDatabaseModel
import com.linc.data.database.table.UsersTable
import com.linc.data.mapper.toDbModel
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class UsersDao {

    suspend fun insertUser(
        model: UserApiModel.Request,
        credentialsId: UUID
    ) = SqlExecutor.executeQuery {
        UsersTable.insertIgnore { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.credentialsId] = credentialsId
            table[UsersTable.name] = model.name
        } get UsersTable.id
    }

    suspend fun selectUser(id: UUID) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq id }
            .limit(1)
            .firstOrNull()
            ?.toDbModel()
    }

    suspend fun selectUsers() = SqlExecutor.executeQuery {
        UsersTable.selectAll().mapNotNull(ResultRow::toDbModel)
    }

    /*suspend fun createEmptyUser(
        email: String,
        name: String,
        gender: String
    ) = SqlExecutor.executeQuery {
        UsersTable.insert { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.name] = name
            table[UsersTable.email] = email
            table[UsersTable.status] = null
            table[UsersTable.gender] = gender
            table[UsersTable.avatarUrl] = String.EMPTY
            table[UsersTable.headerUrl] = String.EMPTY
            table[UsersTable.publicAccess] = true
        } get UsersTable.id
    }

    suspend fun deleteUserById(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.deleteWhere { UsersTable.id eq userId }
    }

    suspend fun existUsername(
        username: String
    ) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.name eq username }.singleOrNull()
    }

    suspend fun existEmail(
        email: String
    ) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.email eq email }.singleOrNull()
    }

    suspend fun searchUsersByQuery(name: String) = SqlExecutor.executeQuery {
        return@executeQuery UsersTable.select {
            (UsersTable.name like "%$name%") or (UsersTable.email like "%$name%")
        }.map(ResultRow::toUserEntity)
    }

    suspend fun searchExtendedUsersByQuery(name: String) = SqlExecutor.executeQuery {
        return@executeQuery CredentialsTable.innerJoin(UsersTable)
            .select { (UsersTable.name like "%$name%") or (UsersTable.email like "%$name%") }
            .map(ResultRow::toUserExtendedEntity)
    }

    suspend fun getUsers() = SqlExecutor.executeQuery {
        UsersTable.selectAll().map { it.toUserEntity() }
    }

    suspend fun getExtendedUsers() = SqlExecutor.executeQuery {
        CredentialsTable.innerJoin(UsersTable)
            .selectAll()
            .map(ResultRow::toUserExtendedEntity)
    }

    suspend fun getExtendedUserById(userId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.innerJoin(UsersTable)
            .select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserByEmail(email: String) = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { UsersTable.email eq email }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserByEmailOrName(
        email: String,
        name: String
    ) = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { (UsersTable.email eq email) or (UsersTable.name eq name) }
            .singleOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserByName(name: String) = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { UsersTable.name eq name }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserAvatar(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserEntity()
            ?.avatarUrl
    }

    suspend fun getUserById(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserEntity()
    }

    suspend fun updateUserName(
        userId: UUID,
        name: String
    ) = updateUserField(userId, UsersTable.name, name)

    suspend fun updateUserStatus(
        userId: UUID,
        status: String
    ) = updateUserField(userId, UsersTable.status, status)

    suspend fun updateUserVisibility(
        userId: UUID,
        isPublic: Boolean
    ) = updateUserField(userId, UsersTable.publicAccess, isPublic)

    suspend fun updateUserGender(
        userId: UUID,
        gender: String
    ) = updateUserField(userId, UsersTable.gender, gender)

    suspend fun updateUserAvatar(
        userId: UUID,
        avatarUrl: String
    ) = updateUserField(userId, UsersTable.avatarUrl, avatarUrl)

    suspend fun updateUserHeader(
        userId: UUID,
        headerUrl: String
    ) = updateUserField(userId, UsersTable.headerUrl, headerUrl)

    *//**
     * Private api
     *//*
    private suspend fun <F> updateUserField(
        userId: UUID,
        field: Column<F>,
        fieldData: F
    ) = SqlExecutor.executeQuery {
        UsersTable.update({ UsersTable.id eq userId }) { table ->
            table[field] = fieldData
        }
    }*/

}