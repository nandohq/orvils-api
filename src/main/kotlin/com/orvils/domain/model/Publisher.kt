package com.orvils.domain.model

import java.time.OffsetDateTime
import java.util.UUID

class Publisher(
    id: UUID? = null,
    val name: String,
    val description: String? = null,
    val country: Country? = null,
    val creationDate: OffsetDateTime = OffsetDateTime.now()
) : BaseModel(id)