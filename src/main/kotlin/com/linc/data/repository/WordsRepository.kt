package com.linc.data.repository

import com.linc.data.database.dao.*
import com.linc.data.database.model.collection.CollectionDbModel
import com.linc.data.database.model.word.WordDbModel
import com.linc.data.mapper.toCollectionApiModel
import com.linc.data.mapper.toWordApiModel
import com.linc.data.network.collection.CollectionApiModel
import com.linc.data.network.word.WordApiModel
import com.linc.extensions.toUUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordsRepository(
    private val wordsDao: WordsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getCollectionWords(id: String): List<WordApiModel> = withContext(ioDispatcher) {
        return@withContext wordsDao.selectByCollection(id.toUUID())
            .getOrNull()
            ?.map(WordDbModel::toWordApiModel)
            .orEmpty()
//        return@withContext emptyList()
    }

//    suspend fun getCollectionWords(id: String): List<WordApiModel> = withContext(ioDispatcher) {
//        return@withContext wordsDao
//            .selectByCollection(id.toUUID())
//            .getOrNull()
//            ?.map(WordDbModel::toWordApiModel)
//            .orEmpty()
//    }

}