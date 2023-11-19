package com.orvils.infrastructure.entity

import com.orvils.domain.model.Collection
import com.orvils.infrastructure.repository.resource.orm.ktorm.zonedDatetime
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.OffsetDateTime
import java.util.UUID

interface CollectionEntity : Entity<CollectionEntity> {
    companion object : Entity.Factory<CollectionEntity>()

    val id: UUID
    var name: String
    var description: String?
    var publisher: PublisherEntity
    val creationDate: OffsetDateTime

    fun toModel() : Collection {
        return Collection(
            id = id,
            name = name,
            description = description,
            publisher = publisher.toModel(),
            creationDate = creationDate
        )
    }
}

object Collections : Table<CollectionEntity>("collection") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var description = varchar("description").bindTo { it.description }
    var publisherId = uuid("publisher_id").references(Publishers) { it.publisher }
    val creationDate = zonedDatetime("creation_date").bindTo { it.creationDate }
}