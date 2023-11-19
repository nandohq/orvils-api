package com.orvils.domain.dto

import java.util.UUID

data class CollectionDTO(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val publisher: SimpleDTO = SimpleDTO()
)
