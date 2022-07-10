package com.linc.extensions

import com.linc.data.network.BaseResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun <T : Any> ApplicationCall.respondSuccess(data: T) = respond(HttpStatusCode.OK, BaseResponse(data))
suspend fun <T : Any> ApplicationCall.respondFailure(data: T) = respond(HttpStatusCode.BadRequest, BaseResponse(data))