package com.linc.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.hibernate.HikariConfigurationUtil
import org.jetbrains.exposed.sql.Database
import java.util.*

object DatabaseManager {

    // Database config
    private const val USER = "esyqanjymgvrbw"
    private const val PASSWORD = "0c122780dd4bd9208daeef799b14ec0a6780575c24fb9946b766e9b229388157"
    private const val DATABASE = "ddj2h6m1efqkv6"
    private const val HOST = "ec2-34-248-169-69.eu-west-1.compute.amazonaws.com"
    private const val PORT = "5432"
    private const val JDBC_URL = "jdbc:postgresql://$HOST:$PORT/$DATABASE?password=$PASSWORD&user=$USER&sslmode=require"
    // Hikari config
    private const val DRIVER = "org.postgresql.Driver"
    private const val TRANSACTION_ISOLATION = "TRANSACTION_REPEATABLE_READ"
    private const val MAX_POOL_SIZE = 3

    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = DRIVER
        config.jdbcUrl = JDBC_URL
        config.maximumPoolSize = MAX_POOL_SIZE
        config.isAutoCommit = false
        config.transactionIsolation = TRANSACTION_ISOLATION
        config.validate()
        return HikariDataSource(config)
    }

}