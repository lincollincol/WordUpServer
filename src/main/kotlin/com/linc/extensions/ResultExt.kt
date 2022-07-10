package com.linc.extensions

fun <T> Result<T>.getOrThrow(message: String) = getOrNull() ?: error(message)