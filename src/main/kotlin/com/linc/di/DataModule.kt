package com.linc.di

import com.linc.data.database.DatabaseManager
import com.linc.data.database.dao.CredentialsDao
import com.linc.data.database.dao.UsersDao
import com.linc.data.repository.UsersRepository
import org.koin.dsl.module

val dataModule = module {

    // Database/DAO
    single<CredentialsDao> { CredentialsDao() }
    single<UsersDao> { UsersDao() }

    // Repositories
    single<UsersRepository> { UsersRepository(get(), get()) }

}