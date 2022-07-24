package com.linc.data.repository

import com.linc.data.database.dao.*
import com.linc.data.database.model.collection.CollectionDbModel
import com.linc.data.mapper.toCollectionApiModel
import com.linc.data.network.collection.CollectionApiModel
import com.linc.extensions.toUUID
import kotlinx.coroutines.*
import java.util.*

class CollectionsRepository(
    private val wordsDao: WordsDao,
    private val translateDao: TranslateDao,
    private val collectionsDao: CollectionsDao,
    private val collectionWordDao: CollectionWordDao,
    private val userWordDao: UserWordDao,
    private val userWordTranslateDao: UserWordTranslateDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun createCollection(
        userId: UUID,
        name: String,
        words: Map<String, List<String>>
    ) = withContext(ioDispatcher) {
        val collectionId = collectionsDao.insertCollection(name, userId)
            .getOrThrow() ?: return@withContext
        words.forEach { word ->
            val wordId = wordsDao.insertWord(word.key)
                .getOrThrow() ?: return@forEach
            val userWordId = userWordDao.insertUserWord(userId, wordId)
                .getOrThrow() ?: return@forEach

            launch { collectionWordDao.insertCollectionWord(collectionId, wordId) }
            launch {
                val translateIds = word.value.map { translate ->
                    async { translateDao.insertTranslate(translate, wordId).getOrNull() }
                }.awaitAll().filterNotNull()
                val wordTranslateIds = translateIds.map { translateId ->
                    async {
                        userWordTranslateDao.insertUserWordTranslate(userWordId, translateId).getOrNull()
                    }
                }.awaitAll().filterNotNull()
            }
        }
    }

    suspend fun getAllCollections(): List<CollectionApiModel> = withContext(ioDispatcher) {
        return@withContext collectionsDao
            .selectAll()
            .getOrNull()
            ?.map(CollectionDbModel::toCollectionApiModel)
            .orEmpty()
    }

    suspend fun getCollection(id: String): List<CollectionApiModel> = withContext(ioDispatcher) {
        return@withContext collectionsDao
            .selectById(id.toUUID())
            .getOrNull()
            ?.map(CollectionDbModel::toCollectionApiModel)
            .orEmpty()
    }

    suspend fun getUserCollections(id: String): List<CollectionApiModel> = withContext(ioDispatcher) {
        return@withContext collectionsDao
            .selectByUserId(id.toUUID())
            .getOrNull()
            ?.map(CollectionDbModel::toCollectionApiModel)
            .orEmpty()
    }

}