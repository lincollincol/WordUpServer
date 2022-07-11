package com.linc.di

import com.linc.data.database.DatabaseManager
import com.linc.data.database.dao.*
import com.linc.data.repository.CollectionsRepository
import com.linc.data.repository.DocumentRepository
import com.linc.data.repository.UsersRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UsersRepository> { UsersRepository(get(), get()) }
    single<DocumentRepository> { DocumentRepository(get()) }
    single<CollectionsRepository> { CollectionsRepository(get(), get(), get(), get(), get(), get()) }
}

val daoModule = module {
    single<CollectionsDao> { CollectionsDao() }
    single<CollectionWordDao> { CollectionWordDao() }
    single<CredentialsDao> { CredentialsDao() }
    single<TranslateDao> { TranslateDao() }
    single<UsersDao> { UsersDao() }
    single<UserWordDao> { UserWordDao() }
    single<UserWordTranslateDao> { UserWordTranslateDao() }
    single<WordsDao> { WordsDao() }
}

val dataModules = listOf(
    daoModule,
    repositoryModule
)