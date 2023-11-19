package com.orvils.infrastructure.entity

import com.orvils.domain.model.Publisher
import com.orvils.infrastructure.repository.resource.orm.ktorm.zonedDatetime
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.OffsetDateTime
import java.util.UUID

interface PublisherEntity : Entity<PublisherEntity> {
    companion object : Entity.Factory<PublisherEntity>()

    val id: UUID
    var name: String
    var description: String?
    var country: CountryEntity?
    val creationDate: OffsetDateTime

    fun toModel() : Publisher {
        return Publisher(
            id = id,
            name = name,
            description = description,
            country = country?.toModel(),
            creationDate = creationDate
        )
    }
}

object Publishers : Table<PublisherEntity>("publisher") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var description = varchar("description").bindTo { it.description }
    var countryId = uuid("country_id").references(Countries) { it.country }
    val creationDate = zonedDatetime("creation_date").bindTo { it.creationDate }
}