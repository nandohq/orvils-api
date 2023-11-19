package com.orvils.domain.exception

import io.ktor.http.*

class EntityAlreadyExistsException(override val message: String, vararg params: Any) :
    BusinessException(HttpStatusCode.Conflict.value, message, params)