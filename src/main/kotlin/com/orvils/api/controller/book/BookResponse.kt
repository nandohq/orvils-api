package com.orvils.api.controller.book

import com.orvils.api.controller.common.resource.SimpleEntityResponse
import com.orvils.domain.model.Book
import com.orvils.domain.model.EditionType
import java.time.OffsetDateTime
import java.util.UUID

data class BookResponse(
    val id: UUID?,
    val title: String,
    val isbn: String,
    val summary: String? = null,
    val edition: EditionResponse,
    val author: SimpleEntityResponse,
    val publisher: SimpleEntityResponse,
    val collection: SimpleEntityResponse? = null,
    val creationDate: OffsetDateTime? = null
) {

    constructor(book: Book) : this(
        id = book.id,
        title = book.title,
        isbn = book.isbn,
        summary = book.summary,
        edition = EditionResponse(book.editionType, book.editionNumber),
        author = SimpleEntityResponse(book.author.id, book.author.name),
        publisher = SimpleEntityResponse(book.publisher.id, book.publisher.name),
        collection = book.collection?.let { SimpleEntityResponse(it.id, it.name) },
        creationDate = book.creationDate
    )
}

data class EditionResponse(
    val type: EditionType,
    val number: Int,
) {
    val label: String = "$number ${type.value}"
}
