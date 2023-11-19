package com.orvils.infrastructure.entity

import com.orvils.domain.model.Book
import com.orvils.domain.model.EditionType
import com.orvils.infrastructure.repository.resource.orm.ktorm.zonedDatetime
import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar
import java.time.OffsetDateTime
import java.util.UUID

interface BookEntity : Entity<BookEntity> {

    companion object : Entity.Factory<BookEntity>()

    var id: UUID
    var title: String
    var isbn: String
    var summary: String?
    var editionType: String
    var editionNumber: Int
    var author: AuthorEntity
    var publisher: PublisherEntity
    var collection: CollectionEntity?
    val creationDate: OffsetDateTime

    fun toModel() : Book {
        return Book(
            id = id,
            title = title,
            isbn = isbn,
            summary = summary,
            editionType = EditionType.valueOf(editionType),
            editionNumber = editionNumber,
            author = author.toModel(),
            publisher = publisher.toModel(),
            collection = collection?.toModel(),
            creationDate = creationDate
        )
    }
}

object Books : Table<BookEntity>("book") {
    val id = uuid("id").primaryKey().bindTo { it.id }
    var title = varchar("title").bindTo { it.title }
    var isbn = varchar("isbn").bindTo { it.isbn }
    var summary = varchar("summary").bindTo { it.summary }
    var editionType = varchar("edition_type").bindTo { it.editionType }
    var editionNumber = int("edition_number").bindTo { it.editionNumber }
    var authorId = uuid("author_id").references(Authors) { it.author }
    var publisherId = uuid("publisher_id").references(Publishers) { it.publisher }
    var collectionId = uuid("collection_id").references(Collections) { it.collection }
    val creationDate = zonedDatetime("creation_date").bindTo { it.creationDate }
}