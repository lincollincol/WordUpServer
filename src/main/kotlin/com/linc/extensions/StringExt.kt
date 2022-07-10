package com.linc.extensions

import java.util.*

fun String.toUUID(): UUID = UUID.fromString(this)