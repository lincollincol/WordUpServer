package com.linc.routes

import com.linc.data.repository.CollectionsRepository
import com.linc.data.repository.DocumentRepository
import com.linc.extensions.respondFailure
import com.linc.extensions.toUUID
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.utils.io.core.*
import java.io.File
import kotlin.io.use

fun Route.collections(
    documentRepository: DocumentRepository,
    collectionsRepository: CollectionsRepository
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
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

}