package com.orvils.config.error

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.orvils.domain.exception.BusinessException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.valiktor.ConstraintViolationException

fun Application.configureErrorHandling() {
    install(StatusPages) {

        exception<Throwable> { call, ex ->
            val detail = handleGeneralException(ex)
            respond(call, detail)
        }

        exception<BadRequestException> { call, ex ->
            val detail = when(val cause = rootCauseOf(ex)) {
                is InvalidFormatException -> handleInvalidFormatException(cause)
                is JsonProcessingException -> handleJsonProcessingException(cause)
                is ConstraintViolationException -> handleConstraintViolationException(cause)
                else -> {
                    val message = cause.message ?: "The request is invalid"
                    ErrorDetail(
                        code = HttpStatusCode.BadRequest.value,
                        message = message,
                        friendlyMessage = message
                    )
                }
            }

            respond(call, detail)
        }

        exception<ValueInstantiationException> { call, ex ->
            val detail = handleValueInstantiationException(ex)
            respond(call, detail)
        }

        exception<ConstraintViolationException> { call, ex ->
            val detail = handleConstraintViolationException(ex)
            respond(call, detail)
        }

        exception<BusinessException> { call, ex ->
            val detail = handleBusinessException(ex)
            respond(call, detail)
        }

    }
}

private suspend fun respond(call: ApplicationCall, detail: ErrorDetail) {
    call.respond(HttpStatusCode.fromValue(detail.code), detail)
}
