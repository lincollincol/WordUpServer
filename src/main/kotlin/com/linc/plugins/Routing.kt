package com.linc.plugins

import com.linc.data.database.DatabaseManager
import com.linc.data.repository.UsersRepository
import com.linc.routes.users
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        DatabaseManager.init()
        val usersRepository: UsersRepository by inject()

        users(usersRepository)

        static("/static") {
            resources("static")
        }
    }
}
