package com.orvils.domain.model

import java.time.OffsetDateTime
import java.util.UUID

class Country(
    id: UUID? = null,
    val name: String,
    val code: String,
    val language: String?,
    val creationDate: OffsetDateTime = OffsetDateTime.now()
) : BaseModel(id)