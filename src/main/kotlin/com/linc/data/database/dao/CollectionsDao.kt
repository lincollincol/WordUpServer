package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.model.collection.CollectionDbModel
import com.linc.data.database.table.CollectionWordTable
import com.linc.data.database.table.CollectionsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.mapper.toCollectionDbModel
import com.linc.data.network.UserApiModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class CollectionsDao {

    suspend fun insertCollection(
        name: String,
        userId: UUID,
    ) = SqlExecutor.executeQuery {
        val foundCollection = CollectionsTable.select {
            (CollectionsTable.name.lowerCase() eq name.lowercase()) and (CollectionsTable.userId eq userId)
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

    suspend fun selectAll() = SqlExecutor.executeQuery {
        CollectionsTable.innerJoin(CollectionWordTable)
            .slice(
                CollectionsTable.id,
                CollectionsTable.userId,
                CollectionsTable.name,
                CollectionWordTable.id.count()
            )
            .selectAll()
            .groupBy(CollectionsTable.id, CollectionsTable.name)
            .map(ResultRow::toCollectionDbModel)
    }

    suspend fun selectById(id: UUID) = SqlExecutor.executeQuery {
        CollectionsTable.innerJoin(CollectionWordTable)
            .slice(
                CollectionsTable.id,
                CollectionsTable.userId,
                CollectionsTable.name,
                CollectionWordTable.id.count()
            )
            .select { CollectionsTable.id eq id }
            .groupBy(CollectionsTable.id, CollectionsTable.name)
            .map(ResultRow::toCollectionDbModel)
    }

    suspend fun selectByUserId(id: UUID) = SqlExecutor.executeQuery {
        CollectionsTable.innerJoin(CollectionWordTable)
            .slice(
                CollectionsTable.id,
                CollectionsTable.userId,
                CollectionsTable.name,
                CollectionWordTable.id.count()
            )
            .select { CollectionsTable.userId eq id }
            .groupBy(CollectionsTable.id, CollectionsTable.name)
            .map(ResultRow::toCollectionDbModel)
    }

}