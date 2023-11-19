package com.orvils.domain.dto

import java.util.UUID

data class PublisherDTO(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val country: SimpleDTO = SimpleDTO()
)
