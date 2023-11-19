package com.orvils.infrastructure.entity

import com.orvils.domain.model.Country
import com.orvils.infrastructure.repository.resource.orm.ktorm.zonedDatetime
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.time.OffsetDateTime
import java.util.UUID

interface CountryEntity : Entity<CountryEntity> {
    companion object : Entity.Factory<CountryEntity>()

    val id: UUID
    var name: String
    var code: String
    var language: String?
    val creationDate: OffsetDateTime

    fun toModel() : Country {
        return Country(
            id = id,
            name = name,
            code = code,
            language = language,
            creationDate = creationDate
        )
    }
}

object Countries : Table<CountryEntity>("country") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var code = varchar("code").bindTo { it.code }
    var language = varchar("language").bindTo { it.language }
    val creationDate = zonedDatetime("creation_date").bindTo { it.creationDate }
}