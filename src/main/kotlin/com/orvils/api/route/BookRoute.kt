package com.orvils.api.route

import com.orvils.api.controller.book.BookController
import com.orvils.api.controller.book.BookCreateRequest
import com.orvils.api.controller.book.BookFilterRequest
import com.orvils.api.controller.book.BookUpdateRequest
import com.orvils.common.extensions.id
import com.orvils.common.extensions.page
import com.orvils.common.extensions.typedQueryParams
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.context.GlobalContext

fun Route.bookRouting() {
    val controller: BookController = GlobalContext.get().get()

    route("/books") {
        post<BookCreateRequest> {
            val createdBook = controller.create(it)

            call.response.header(HttpHeaders.Location, "/books/${createdBook.id}")
            call.respond(HttpStatusCode.Companion.Created, createdBook)
        }

        get("/{id}") {
            val returnedBook = controller.getOne(call.id())
            call.respond(HttpStatusCode.OK, returnedBook)
        }

        patch<BookUpdateRequest>("/{id}") {
            val updatedBook = controller.update(call.id(), it)
            call.respond(HttpStatusCode.OK, updatedBook)
        }

        delete("/{id}") {
            controller.delete(call.id())
            call.respond(HttpStatusCode.NoContent)
        }

        get {
            val pageRequest = call.page()
            val filterRequest = call.typedQueryParams(BookFilterRequest::class)

            val books = controller.filter(filterRequest, pageRequest)
            call.respond(HttpStatusCode.OK, books)
        }
    }
}