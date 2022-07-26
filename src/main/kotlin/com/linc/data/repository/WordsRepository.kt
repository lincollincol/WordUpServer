package com.linc.data.repository

import com.linc.data.database.dao.*
import com.linc.data.database.model.collection.CollectionDbModel
import com.linc.data.database.model.word.UserWordDbModel
import com.linc.data.database.model.word.WordDbModel
import com.linc.data.mapper.toCollectionApiModel
import com.linc.data.mapper.toUserWordApiModel
import com.linc.data.mapper.toWordApiModel
import com.linc.data.network.collection.CollectionApiModel
import com.linc.data.network.word.UserWordApiModel
import com.linc.data.network.word.WordApiModel
import com.linc.extensions.toUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsRepository(
    private val wordsDao: WordsDao,
    private val userWordDao: UserWordDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCollectionWords(id: String): List<WordApiModel> = withContext(ioDispatcher) {
        return@withContext wordsDao.selectByCollection(id.toUUID())
            .getOrNull()
            ?.map(WordDbModel::toWordApiModel)
            .orEmpty()
    }

    suspend fun getCollectionUserWords(
        collectionId: String,
        userId: String,
        limit: Int,
        offset: Int,
    ): List<UserWordApiModel> = withContext(ioDispatcher) {
        return@withContext userWordDao
            .selectByCollection(
                collectionId = collectionId.toUUID(),
                userId = userId.toUUID(),
                limit = limit,
                offset = offset
            )
            .getOrNull()
            ?.map(UserWordDbModel::toUserWordApiModel)
            .orEmpty()
    }

    suspend fun getUserWords(id: String): List<UserWordApiModel> = withContext(ioDispatcher) {
        return@withContext userWordDao.selectByUser(id.toUUID())
            .getOrNull()
            ?.map(UserWordDbModel::toUserWordApiModel)
            .orEmpty()
    }

//    suspend fun getCollectionWords(id: String): List<WordApiModel> = withContext(ioDispatcher) {
//        return@withContext wordsDao
//            .selectByCollection(id.toUUID())
//            .getOrNull()
//            ?.map(WordDbModel::toWordApiModel)
//            .orEmpty()
//    }

}