package com.orvils.config.error

import java.time.OffsetDateTime

data class ErrorDetail(
    val code: Int,
    val message: String,
    val friendlyMessage: String? = null,
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val invalidFields: List<InputViolation>? = null
)

data class InputViolation(
    val target: String,
    val reason: String
)
