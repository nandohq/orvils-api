package com.orvils.api.controller.book

import com.orvils.domain.filter.BookFilter
import java.util.*

data class BookFilterRequest(
    val id: String? = null,
    val title: String? = null,
    val isbn: String? = null,
    val summary: String? = null
)

fun BookFilterRequest.toBookFilter() = BookFilter(
    id = id?.let { UUID.fromString(it) },
    title = title,
    isbn = isbn,
    summary = summary
)
