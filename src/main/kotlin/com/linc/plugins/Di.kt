package com.linc.plugins

import com.linc.di.appModules
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureDi() {
    Koin.install(this) {
        SLF4JLogger()
        modules(appModules)
    }
}