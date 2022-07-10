package com.linc.data.network

sealed class UserApiModel(
    val name: String
) {
    class Request(
        name: String,
        val password: String,
        val login: String
    ) : UserApiModel(name)

    class Response(
        name: String,
        val id: String
    ) : UserApiModel(name)
}