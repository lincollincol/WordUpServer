package com.linc.extensions

fun <T> Result<T>.getOrException(message: String) = getOrNull() ?: error(message)