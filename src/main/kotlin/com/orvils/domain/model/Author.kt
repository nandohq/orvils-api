package com.orvils.domain.model

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

class Author(
    id: UUID? = null,
    val name: String,
    val birthday: LocalDate? = null,
    val nationality: Country? = null,
    val creationDate: OffsetDateTime = OffsetDateTime.now()
) : BaseModel(id)