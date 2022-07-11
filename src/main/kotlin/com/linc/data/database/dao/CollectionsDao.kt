package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CollectionWordTable
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class CollectionsDao {

    suspend fun insertCollection(
        name: String,
        userId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundCollection = CollectionsTable.select {
            (CollectionsTable.name eq name) and (CollectionsTable.userId eq userId)
        }
            .limit(1)
            .firstOrNull()
        if(foundCollection != null) {
            error("Collection with this name already exist!")
        }
        CollectionsTable.insertIgnore { table ->
            table[CollectionsTable.id] = UUID.randomUUID()
            table[CollectionsTable.userId] = userId
            table[CollectionsTable.name] = name
        } get CollectionsTable.id
    }

}