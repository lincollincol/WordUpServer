package com.linc.routes

import com.linc.data.network.UserApiModel
import com.linc.data.repository.UsersRepository
import com.linc.extensions.respondFailure
import com.linc.extensions.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.users(
    usersRepository: UsersRepository
) {

    post<UserApiModel.Request>("/users") {
        try {
            usersRepository.createUser(it)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

}