package com.orvils.infrastructure.repository.resource.pagination

data class Pageable(
    val page: Int = 0,
    val size: Int = 10,
    val sort: List<Order> = listOf()
) {
    val offset: Int get() = page * size
}
