package com.orvils.domain.dto

import java.time.LocalDate
import java.util.UUID

data class AuthorDTO(
    val id: UUID? = null,
    val name: String,
    val birthday: LocalDate? = null,
    val nationality: SimpleDTO = SimpleDTO()
)
