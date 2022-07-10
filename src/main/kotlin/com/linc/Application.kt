package com.linc

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.linc.plugins.*

fun main() {
    embeddedServer(Netty, port = 8885, host = "0.0.0.0") {
        configureDi()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureSecurity()
    }.start(wait = true)
}
