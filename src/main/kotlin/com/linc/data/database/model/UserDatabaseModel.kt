package com.linc.data.database.model

import java.util.*

data class UserDatabaseModel(
    val id: UUID,
    val credentialsId: UUID,
    val name: String
)