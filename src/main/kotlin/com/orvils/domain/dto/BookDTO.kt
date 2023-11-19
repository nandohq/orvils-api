package com.orvils.domain.dto

import com.orvils.domain.model.EditionType
import java.util.UUID

data class BookDTO( //TODO: remover nullables
    val id: UUID? = null,
    val title: String? = null,
    val isbn: String? = null,
    val summary: String? = null,
    val editionType: EditionType? = null,
    val editionNumber: Int? = null,
    val author: SimpleDTO = SimpleDTO(),
    val publisher: SimpleDTO = SimpleDTO(),
    val collection: SimpleDTO = SimpleDTO()
)
