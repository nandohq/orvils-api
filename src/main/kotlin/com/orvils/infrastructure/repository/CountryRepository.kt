package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.domain.model.Country
import com.orvils.infrastructure.entity.Countries
import org.koin.core.annotation.Single
import org.ktorm.dsl.*
import java.util.UUID

@Single
class CountryRepository {

    private val db = DBManager.db()

    fun save(country: Country): Country {
        if (country.id == null) {
            db.insert(Countries) { setColumns(this, country) }
        } else {
            db.update(Countries) {
                setColumns(this, country)
                where { it.id eq country.id }
            }
        }

        return country
    }

    private fun setColumns(builder: AssignmentsBuilder, country: Country) {
        country.id?.let { builder.set(Countries.id, it) }
        builder.set(Countries.name, country.name)
        builder.set(Countries.code, country.code)
        builder.set(Countries.language, country.language)
    }

    fun delete(id: UUID) { db.delete(Countries) { it.id eq id } }

    fun findById(id: UUID) : Country? {
        val entity = db.from(Countries).select()
            .where { Countries.id eq id }
            .map { row -> Countries.createEntity(row) }
            .firstOrNull()

        return entity?.toModel()
    }

}