package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UsersDao
import com.linc.data.database.model.UserDatabaseModel
import com.linc.data.mapper.toApiModel
import com.linc.data.network.UserApiModel
import com.linc.extensions.getOrThrow
import com.linc.extensions.toUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(
    private val usersDao: UsersDao,
    private val credentialsDao: CredentialsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun createUser(
        model: UserApiModel.Request
    ) = withContext(ioDispatcher) {
        val credentialsId = credentialsDao.insertCredentials(model)
            .getOrThrow()
            ?: error("Credentials not created!")
        usersDao.insertUser(model, credentialsId)
            .getOrThrow("Cannot create user!")
    }

    suspend fun getUsers(): List<UserApiModel.Response> = withContext(ioDispatcher) {
        return@withContext usersDao.selectUsers().getOrThrow("Cannot load users!")
            .map(UserDatabaseModel::toApiModel)
    }

    suspend fun getUser(id: String) = withContext(ioDispatcher) {
        return@withContext usersDao.selectUser(id.toUUID())
            .getOrThrow("User not found!")
            .toApiModel()
    }

}