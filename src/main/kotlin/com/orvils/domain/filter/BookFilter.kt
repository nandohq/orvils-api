package com.orvils.domain.filter

import java.util.UUID

data class BookFilter(
    val id: UUID? = null,
    val title: String? = null,
    val isbn: String? = null,
    val summary: String? = null
)