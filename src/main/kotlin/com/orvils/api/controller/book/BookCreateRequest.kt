package com.orvils.api.controller.book

import com.orvils.api.common.validation.*
import com.orvils.domain.dto.BookDTO
import com.orvils.domain.dto.SimpleDTO
import com.orvils.domain.model.*
import org.valiktor.validate
import java.util.UUID

data class BookCreateRequest(
    val title: String? = null,
    val isbn: String? = null,
    val summary: String? = null,
    val editionType: String? = null,
    val editionNumber: String? = null,
    val authorId: String? = null,
    val publisherId: String? = null,
    val collectionId: String? = null,
) {
    init {
        validate(this) {
            validate(BookCreateRequest::title).hasBeenInformed()
            validate(BookCreateRequest::isbn).hasBeenInformed().isIsbn()
            validate(BookCreateRequest::editionType).isEnumeration(EditionType::class)
            validate(BookCreateRequest::editionNumber).isNumber(onlyPositive = true)
            validate(BookCreateRequest::collectionId).isUUID()
            validate(BookCreateRequest::authorId).hasBeenInformed().isUUID()
            validate(BookCreateRequest::publisherId).hasBeenInformed().isUUID()
        }
    }

    fun toDTO(): BookDTO {
        return BookDTO(
            title = title!!,
            isbn = isbn!!,
            summary = summary,
            editionType = EditionType.valueOf(editionType!!.uppercase()),
            editionNumber = editionNumber!!.toInt(),
            author = SimpleDTO(id = authorId?.let { UUID.fromString(it) }),
            publisher = SimpleDTO(id = publisherId?.let { UUID.fromString(it) }),
            collection = SimpleDTO(id = collectionId?.let { UUID.fromString(it) }),
        )
    }
}

