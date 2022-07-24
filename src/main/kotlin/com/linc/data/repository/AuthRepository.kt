package com.linc.data.repository

import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UsersDao
import com.linc.data.mapper.toApiModel
import com.linc.data.network.UserApiModel
import com.linc.extensions.getOrException
import com.linc.wordcard.data.network.model.auth.SignInApiModel
import com.linc.wordcard.data.network.model.auth.SignUpApiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val usersDao: UsersDao,
    private val credentialsDao: CredentialsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun signUp(
        model: SignUpApiModel
    ): UserApiModel = withContext(ioDispatcher) {
        val credentialsId = credentialsDao.insertCredentials(model.login, model.password)
            .getOrThrow()
            ?: error("Cannot create credentials!")
        val userId = usersDao.insertUser(model.name, credentialsId)
            .getOrException("Cannot create user!")
        return@withContext usersDao.selectUser(userId)
            .getOrException("User not found!")
            .toApiModel()
    }

    suspend fun signIn(
        model: SignInApiModel
    ): UserApiModel = withContext(ioDispatcher) {
        val credentials = credentialsDao.selectCredentials(model.login)
            .getOrException("User not found!")
        if(credentials.password != model.password) {
            error("Invalid password!")
        }
        val user = usersDao.selectByCredentials(credentials.id)
            .getOrException("User not found!")
            .toApiModel()
        return@withContext user 
    }

}