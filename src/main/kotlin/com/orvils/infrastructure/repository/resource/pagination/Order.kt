package com.orvils.infrastructure.repository.resource.pagination

data class Order(
    val field: String,
    val direction: Direction = Direction.ASC
)