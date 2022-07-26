package com.linc.routes

import io.ktor.server.routing.*

fun Route.words() {

    get("/words") {

    }

    get("/words/{id}") {

    }

    get("/users/{id}/words") {

    }

    get("/users/{userId}/words/{wordId}") {

    }

}