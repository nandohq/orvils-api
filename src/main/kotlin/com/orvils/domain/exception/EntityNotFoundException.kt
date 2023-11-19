package com.orvils.domain.exception

import io.ktor.http.*

class EntityNotFoundException(override val message: String) :
    BusinessException(HttpStatusCode.NotFound.value, message)