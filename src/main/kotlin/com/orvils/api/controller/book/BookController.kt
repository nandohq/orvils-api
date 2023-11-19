package com.orvils.api.controller.book

import com.orvils.api.controller.common.resource.IdRequest
import com.orvils.api.controller.common.resource.PageRequest
import com.orvils.api.controller.common.resource.toPageable
import com.orvils.common.extensions.toCamelCase
import com.orvils.domain.service.BookService
import com.orvils.infrastructure.repository.resource.pagination.Page
import org.koin.core.annotation.Single

@Single
class BookController(private val bookService: BookService) {

    fun create(bookRequest: BookCreateRequest) : BookResponse {
        val candidate = bookRequest.toDTO()
        val createdBook = bookService.create(candidate)

        return BookResponse(createdBook)
    }

    fun update(idRequest: IdRequest, updateRequest: BookUpdateRequest) : BookResponse {
        val changes = updateRequest.changedFields().associate { it.field.toCamelCase() to it.value }.toMutableMap()
        val updatedBook = bookService.update(idRequest.asUUID(), changes)

        return BookResponse(updatedBook)
    }

    fun getOne(idRequest: IdRequest) : BookResponse {
        val returnedBook = bookService.getOne(idRequest.asUUID())
        return BookResponse(returnedBook)
    }

    fun delete(idRequest: IdRequest) {
        bookService.delete(idRequest.asUUID())
    }

    fun filter(filterRequest: BookFilterRequest, pageRequest: PageRequest) : Page<BookResponse> {
        val pageable = pageRequest.toPageable()
        val filter = filterRequest.toBookFilter()

        val pageBooks = bookService.filter(filter, pageable)
        /* TODO:
            1 - criar response para livros filtrados contendo autor e editora
            2 - retornar o total de paginas disponiveis -> OK
            3 - implementar o filtro por autor e editora
            4 - implementar atributo "displayFields"
         */
        return Page(pageBooks.content.map { BookResponse(it) }, pageBooks.pageable, pageBooks.totalRecords)
    }

}