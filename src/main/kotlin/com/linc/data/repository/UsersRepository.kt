package com.linc.data.repository

import com.linc.data.database.dao.UsersDao
import com.linc.data.database.model.user.UserDbModel
import com.linc.data.mapper.toApiModel
import com.linc.data.network.UserApiModel
import com.linc.extensions.getOrException
import com.linc.extensions.toUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(
    private val usersDao: UsersDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getUsers(): List<UserApiModel> = withContext(ioDispatcher) {
        return@withContext usersDao.selectUsers().getOrException("Cannot load users!")
            .map(UserDbModel::toApiModel)
    }

    suspend fun getUser(id: String): UserApiModel = withContext(ioDispatcher) {
        return@withContext usersDao.selectUser(id.toUUID())
            .getOrException("User not found!")
            .toApiModel()
    }

}