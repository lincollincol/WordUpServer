package com.linc.data.database.model.auth

import java.util.*

data class CredentialsDbModel(
    val id: UUID,
    val password: String,
    val login: String
)