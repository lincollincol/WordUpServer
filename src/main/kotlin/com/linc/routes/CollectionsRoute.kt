package com.linc.routes

import com.linc.data.repository.CollectionsRepository
import com.linc.data.repository.DocumentRepository
import com.linc.data.repository.WordsRepository
import com.linc.extensions.respondFailure
import com.linc.extensions.respondSuccess
import com.linc.extensions.toUUID
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*
import java.io.File
import kotlin.io.use

/**
 * Collections resources
 * Local words resources (for user custom params)
 */
fun Route.collections(
    documentRepository: DocumentRepository,
    collectionsRepository: CollectionsRepository,
    wordsRepository: WordsRepository
) {

    post("/collections") {
        try {
            val multipartParts = call.receiveMultipart().readAllParts()
            val data = multipartParts.filterIsInstance<PartData.FileItem>().firstOrNull()
            val formParameters = multipartParts.filterIsInstance<PartData.FormItem>()
            val userId = formParameters.firstOrNull { it.name == "userId" }?.value.orEmpty().toUUID()
            val name = formParameters.firstOrNull { it.name == "name" }?.value.orEmpty()
            val words = data?.streamProvider?.invoke()?.use {
                documentRepository.loadDocumentWords(it)
            }.orEmpty()
            collectionsRepository.createCollection(userId, name, words)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

    get("/collections") {
        try {
            val userId = call.parameters["userId"]
            val collections = when {
                !userId.isNullOrEmpty() -> collectionsRepository.getUserCollections(userId)
                else -> collectionsRepository.getAllCollections()
            }
            call.respondSuccess(collections)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

    get("/collections/{id}") {
        try {
            val id = call.parameters["id"].toString()
            val collection = collectionsRepository.getCollection(id)
            call.respondSuccess(collection)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

    get("/collections/{id}/words") {
        try {
            val id = call.parameters["id"].toString()
            val words = wordsRepository.getCollectionWords(id)
            call.respondSuccess(words)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

    get("/collections/{collectionId}/words/{wordId}") {
        try {
            val id = call.parameters["id"].toString()
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

}