package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.domain.model.Collection
import com.orvils.infrastructure.entity.Collections
import com.orvils.infrastructure.entity.Publishers
import org.koin.core.annotation.Single
import org.ktorm.dsl.*
import java.util.UUID

@Single
class CollectionRepository {

    private val db = DBManager.db()

    fun save(collection: Collection): Collection {
        if (collection.isNew) {
            db.insert(Collections) { setColumns(this, collection) }
        } else {
            db.update(Collections) {
                setColumns(this, collection)
                where { it.id eq collection.id }
            }
        }

        return collection
    }

    private fun setColumns(builder: AssignmentsBuilder, collection: Collection) {
        builder.set(Collections.id, collection.id)
        builder.set(Collections.name, collection.name)
        builder.set(Collections.creationDate, collection.creationDate)
        builder.set(Publishers.id, collection.publisher.id)

        collection.description?.let { builder.set(Collections.description, it) }
    }

    fun delete(id: UUID) { db.delete(Collections) { it.id eq id } }

    fun findById(id: UUID) : Collection? {
        val entity = db.from(Collections).select()
            .where { Collections.id eq id }
            .map { row -> Collections.createEntity(row, withReferences = true) }
            .firstOrNull()

        return entity?.toModel()
    }

//    fun filter(filter: CollectionFilter, pageable: Pageable) : Page<Collection> = transaction {
//        val query = CollectionTable.selectAll()
//
//        filter.id?.let { query.andWhere { CollectionTable.id eq it } }
//        filter.isbn?.let { query.andWhere { CollectionTable.isbn eq it } }
//        filter.title?.let { query.andWhere { CollectionTable.title ilike "%$it%" } }
//
//        pageable.sort.forEach { order ->
//            CollectionTable.columns.find { it.name.equals(order.field, true) }?.let {
//                query.orderBy(it to SortOrder.valueOf(order.direction.name))
//            }
//        }
//
//        query.limit(pageable.size, pageable.offset)
//
//        val total = query.count()
//        val collections = CollectionEntity.wrapRows(query).map { it.toCollection() }
//
//        Page(collections, pageable, total)
//    }
}