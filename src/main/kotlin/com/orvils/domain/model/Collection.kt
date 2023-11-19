package com.orvils.domain.model

import java.time.OffsetDateTime
import java.util.UUID

class Collection(
    id: UUID? = null,
    val name: String,
    val description: String? = null,
    val publisher: Publisher,
    val creationDate: OffsetDateTime = OffsetDateTime.now()
) : BaseModel(id)