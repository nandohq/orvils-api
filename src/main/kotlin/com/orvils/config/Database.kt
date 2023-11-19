package com.orvils.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.ktorm.database.Database

object DBManager {

    private lateinit var database : Database

    fun configureDatabases() {
        val config = HikariConfig("/db/database.properties")
        val dataSource = HikariDataSource(config)

        database = Database.connect(dataSource)

        val flyway = Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(dataSource)
            .load()

        flyway.migrate()
    }

    fun db() : Database {
        return database
    }
}
