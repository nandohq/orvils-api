package com.orvils.domain.model

import java.time.OffsetDateTime
import java.util.UUID

class Book(
    id: UUID? = null,
    val title: String,
    val isbn: String,
    val summary: String? = null,
    val editionType: EditionType = EditionType.EDITION,
    val editionNumber: Int = 1,
    val author: Author,
    val publisher: Publisher,
    val collection: Collection? = null,
    val creationDate: OffsetDateTime = OffsetDateTime.now()
) : BaseModel(id)