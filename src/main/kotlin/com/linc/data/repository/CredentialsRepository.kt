package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UsersDao
import com.linc.data.mapper.toApiModel
import com.linc.extensions.getOrThrow
import com.linc.extensions.toUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CredentialsRepository(
    private val credentialsDao: CredentialsDao,
    private val usersDao: UsersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun createUser(id: String) = withContext(ioDispatcher) {

        return@withContext usersDao.selectUser(id.toUUID())
            .getOrThrow("User not found!")
            .toApiModel()
    }

}