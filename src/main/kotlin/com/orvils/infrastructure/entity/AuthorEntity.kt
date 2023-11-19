package com.orvils.infrastructure.entity

import com.orvils.domain.model.Author
import com.orvils.infrastructure.repository.resource.orm.ktorm.zonedDatetime
import org.ktorm.entity.Entity
import org.ktorm.schema.*
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

interface AuthorEntity : Entity<AuthorEntity> {
    companion object : Entity.Factory<AuthorEntity>()

    val id: UUID
    var name: String
    var birthday: LocalDate?
    var nationality: CountryEntity?
    val creationDate: OffsetDateTime

    fun toModel() : Author {
        return Author(
            id = id,
            name = name,
            birthday = birthday,
            nationality = nationality?.toModel(),
            creationDate = creationDate
        )
    }
}

object Authors : Table<AuthorEntity>("author") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var birthday = date("birthday").bindTo { it.birthday }
    var nationalityId = uuid("country_id").references(Countries) { it.nationality }
    val creationDate = zonedDatetime("creation_date").bindTo { it.creationDate }
}