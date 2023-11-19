package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.domain.filter.BookFilter
import com.orvils.domain.model.Book
import com.orvils.domain.model.fields
import com.orvils.infrastructure.entity.*
import com.orvils.infrastructure.repository.resource.pagination.Page
import com.orvils.infrastructure.repository.resource.pagination.Pageable
import org.koin.core.annotation.Single
import org.ktorm.dsl.*
import org.ktorm.expression.OrderByExpression
import org.ktorm.expression.OrderType
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.support.postgresql.ilike
import java.util.UUID

@Single
class BookRepository {

    private val db = DBManager.db()

    fun save(book: Book): Book {
        if (book.isNew) {
            db.insert(Books) { setColumns(this, book) }
        } else {
            db.update(Books) {
                setColumns(this, book)
                where { it.id eq book.id }
            }
        }

        return book
    }

    private fun setColumns(builder: AssignmentsBuilder, book: Book) {
        builder.set(Books.id, book.id)
        builder.set(Books.title, book.title)
        builder.set(Books.isbn, book.isbn)
        builder.set(Books.summary, book.summary)
        builder.set(Books.editionType, book.editionType.name)
        builder.set(Books.editionNumber, book.editionNumber)
    }

    fun delete(id: UUID) { db.delete(Books) { it.id eq id } }

    fun findById(id: UUID) : Book? {
       val entity = db.from(Books)
           .innerJoin(Authors, on = Books.authorId eq Authors.id)
           .innerJoin(Publishers, on = Books.publisherId eq Publishers.id)
           .leftJoin(Collections, on = Books.collectionId eq Collections.id)
           .leftJoin(Countries, on = Authors.nationalityId eq Countries.id) //TODO: verificar uma forma de não fazer join com o que não precisa
           //.joinReferencesAndSelect()
            .select()
            .where { Books.id eq id }
            .map { Books.createEntity(it, true) }
            .firstOrNull()

        return entity?.toModel()
    }

    fun findByIsbn(isbn: String) : Book? {
        val entity = findWhere { Books.isbn eq isbn }.map { Books.createEntity(it, true) }.firstOrNull()
        return entity?.toModel()
    }

    private fun findWhere(condition: () -> ColumnDeclaring<Boolean>) : Query {
        return db.from(Books).select().where(condition)
    }

    fun filter(filter: BookFilter, pageable: Pageable) : Page<Book> {
        val sorts = pageable.sort.mapNotNull { order ->
            Books.columns.find { it.name.equals(order.field, true) }?.let {
                OrderByExpression(it.asExpression(), OrderType.valueOf(order.direction.value))
            }
        }

        val query = db.from(Books).select()
            .whereWithConditions { conditions ->
                filter.id?.let { conditions += Books.id eq it }
                filter.isbn?.let { conditions += Books.isbn eq it }
                filter.title?.let { conditions += Books.title ilike "%$it%" }
            }
            .limit(pageable.size, pageable.offset)
            .orderBy(*sorts.toTypedArray())

        val total = query.totalRecordsInAllPages
        val books = query.map { Books.createEntity(it, true).toModel() }

        return Page(books, pageable, total)
    }
}
