package com.linc.data.database.model

import java.util.*

data class CredentialsDatabaseModel(
    val id: UUID,
    val password: String,
    val login: String
)