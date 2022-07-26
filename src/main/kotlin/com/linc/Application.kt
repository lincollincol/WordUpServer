package com.linc

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.linc.plugins.*

fun main() {
//    embeddedServer(Netty, port = 8885, host = "0.0.0.0") {
//    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureDi()
        configureRouting()
        configureSerialization()
        configureMonitoring()
        configureSecurity()
    }.start(wait = true)
}
