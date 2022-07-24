package com.linc.routes

import com.linc.data.network.UserApiModel
import com.linc.data.repository.AuthRepository
import com.linc.data.repository.UsersRepository
import com.linc.extensions.respondFailure
import com.linc.extensions.respondSuccess
import com.linc.wordcard.data.network.model.auth.SignInApiModel
import com.linc.wordcard.data.network.model.auth.SignUpApiModel
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.auth(
    authRepository: AuthRepository
) {

    post<SignInApiModel>("/sign-in") {
        try {
            val user = authRepository.signIn(it)
            call.respondSuccess(user)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }

    post<SignUpApiModel>("/sign-up") {
        try {
            val user = authRepository.signUp(it)
            call.respondSuccess(user)
        } catch (e: Exception) {
            call.respondFailure(e.localizedMessage)
        }
    }


}