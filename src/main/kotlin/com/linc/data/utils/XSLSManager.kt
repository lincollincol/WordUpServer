package com.linc.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class XSLSManager (
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        private const val WORD_ORIGINAL_SHEET = "original"
        private const val WORD_TRANSLATE_SHEET = "translate"
    }

    suspend fun readDocument(doc: File): Map<String, List<String>> = withContext(ioDispatcher) {
        return@withContext FileInputStream(doc).use { readDocument(it) }
    }

    suspend fun readDocument(stream: InputStream): Map<String, List<String>> = withContext(ioDispatcher) {
        val book = WorkbookFactory.create(stream)
        val wordsSheet = book.getSheet(WORD_ORIGINAL_SHEET)
        val translateSheet = book.getSheet(WORD_TRANSLATE_SHEET)
        val words = async { readSheetRows(wordsSheet).mapNotNull { it.firstOrNull() } }
        val translate = async { readSheetRows(translateSheet).map(List<String>::distinct) }
        return@withContext words.await().zip(translate.await()).toMap()
    }

    private suspend fun readSheetRows(
        sheet: Sheet
    ): List<List<String>> = withContext(ioDispatcher) {
        val rows = mutableListOf<List<String>>()
        for(row in sheet) {
            rows.add(readRowCells(row))
        }
        return@withContext rows
    }

    private fun readRowCells(row: Row): List<String> {
        val cells = mutableListOf<String?>()
        for(cell in row) {
            if(cell.cellType == Cell.CELL_TYPE_STRING) {
                cells.add(cell.stringCellValue)
            }
        }
        return cells.filterNotNull()
    }
}