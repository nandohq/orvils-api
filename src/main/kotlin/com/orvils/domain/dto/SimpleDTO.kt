package com.orvils.domain.dto

import java.util.UUID

data class SimpleDTO(
    val id: UUID? = null
) {
    constructor(id: String?) : this(id?.let { UUID.fromString(it) })
}
