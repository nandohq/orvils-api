package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.domain.model.Publisher
import com.orvils.infrastructure.entity.Publishers
import org.koin.core.annotation.Single
import org.ktorm.dsl.*
import java.util.UUID

@Single
class PublisherRepository {

    private val db = DBManager.db()

    fun save(publisher: Publisher): Publisher {
        if (publisher.isNew) {
            db.insert(Publishers) { setColumns(this, publisher) }
        } else {
            db.update(Publishers) {
                setColumns(this, publisher)
                where { it.id eq publisher.id }
            }
        }

        return publisher
    }

    private fun setColumns(builder: AssignmentsBuilder, publisher: Publisher) {
        builder.set(Publishers.id, publisher.id)
        builder.set(Publishers.name, publisher.name)
        builder.set(Publishers.creationDate, publisher.creationDate)

        publisher.country?.let { builder.set(Publishers.countryId, it.id) }
        publisher.description?.let { builder.set(Publishers.description, it) }
    }

    fun delete(id: UUID) { db.delete(Publishers) { it.id eq id } }

    fun findById(id: UUID) : Publisher? {
        val entity = db.from(Publishers).select()
            .where { Publishers.id eq id }
            .map { row -> Publishers.createEntity(row, withReferences = true) }
            .firstOrNull()

        return entity?.toModel()
    }

//    fun filter(filter: PublisherFilter, pageable: Pageable) : Page<Publisher> = transaction {
//        val query = PublisherTable.selectAll()
//
//        filter.id?.let { query.andWhere { PublisherTable.id eq it } }
//        filter.isbn?.let { query.andWhere { PublisherTable.isbn eq it } }
//        filter.title?.let { query.andWhere { PublisherTable.title ilike "%$it%" } }
//
//        pageable.sort.forEach { order ->
//            PublisherTable.columns.find { it.name.equals(order.field, true) }?.let {
//                query.orderBy(it to SortOrder.valueOf(order.direction.name))
//            }
//        }
//
//        query.limit(pageable.size, pageable.offset)
//
//        val total = query.count()
//        val publishers = PublisherEntity.wrapRows(query).map { it.toPublisher() }
//
//        Page(publishers, pageable, total)
//    }
}