package com.linc.data.repository

import com.linc.data.utils.XSLSManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class DocumentRepository(
    private val xslxManager: XSLSManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun loadDocumentWords(
        stream: InputStream
    ): Map<String, List<String>> = withContext(ioDispatcher) {
        return@withContext xslxManager.readDocument(stream)
    }

}