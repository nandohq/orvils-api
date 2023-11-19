package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.domain.model.Author
import com.orvils.infrastructure.entity.Authors
import org.koin.core.annotation.Single
import org.ktorm.dsl.*
import java.util.UUID

@Single
class AuthorRepository {

    private val db = DBManager.db()

    fun save(author: Author): Author {
        if (author.id == null) {
            db.insert(Authors) { setColumns(this, author) }
        } else {
            db.update(Authors) {
                setColumns(this, author)
                where { it.id eq author.id }
            }
        }

        return author
    }

    private fun setColumns(builder: AssignmentsBuilder, author: Author) {
        author.id?.let { builder.set(Authors.id, it) }
        builder.set(Authors.name, author.name)
        builder.set(Authors.birthday, author.birthday)

        author.nationality?.let { country ->
            builder.set(Authors.nationalityId, country.id)
        }
    }

    fun delete(id: UUID) { db.delete(Authors) { it.id eq id } }

    fun findById(id: UUID) : Author? {
        val entity = db.from(Authors).select()
            .where { Authors.id eq id }
            .map { row -> Authors.createEntity(row, withReferences = true) }
            .firstOrNull()

        return entity?.toModel()
    }

//    fun filter(filter: AuthorFilter, pageable: Pageable) : Page<Author> = transaction {
//        val query = AuthorTable.selectAll()
//
//        filter.id?.let { query.andWhere { AuthorTable.id eq it } }
//        filter.isbn?.let { query.andWhere { AuthorTable.isbn eq it } }
//        filter.title?.let { query.andWhere { AuthorTable.title ilike "%$it%" } }
//
//        pageable.sort.forEach { order ->
//            AuthorTable.columns.find { it.name.equals(order.field, true) }?.let {
//                query.orderBy(it to SortOrder.valueOf(order.direction.name))
//            }
//        }
//
//        query.limit(pageable.size, pageable.offset)
//
//        val total = query.count()
//        val authors = AuthorEntity.wrapRows(query).map { it.toAuthor() }
//
//        Page(authors, pageable, total)
//    }
}