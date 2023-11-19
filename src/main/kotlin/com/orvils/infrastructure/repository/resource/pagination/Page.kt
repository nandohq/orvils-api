package com.orvils.infrastructure.repository.resource.pagination

import kotlin.math.ceil

class Page<T>(
    val content: List<T>,
    val pageable: Pageable,
    val totalRecords: Int = content.size
) {
    val totalPages: Int = if (pageable.size == 0) 1 else ceil(totalRecords.toDouble() / pageable.size).toInt()
}