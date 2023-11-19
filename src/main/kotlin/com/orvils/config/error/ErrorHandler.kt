package com.orvils.config.error

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.ValueInstantiationException
import com.orvils.api.common.validation.CustomConstraint
import com.orvils.domain.exception.BusinessException
import com.orvils.common.extensions.toSnakeCase
import com.orvils.common.message.Messages
import io.ktor.http.*
import org.slf4j.LoggerFactory
import org.valiktor.ConstraintViolationException
import org.valiktor.i18n.mapToMessage
import java.text.MessageFormat
import kotlin.reflect.KClass

private val logger = LoggerFactory.getLogger("[ErrorHandler]")

fun handleGeneralException(ex: Throwable) : ErrorDetail {
    logger.error("An unknown error occurred", ex)

    return ErrorDetail(
        code = HttpStatusCode.ExpectationFailed.value,
        message = "Unable to process the request",
        friendlyMessage = "Unable to process the request. Please, try again later"
    )
}

/** BUSINESS **/

fun handleBusinessException(ex: BusinessException) : ErrorDetail {
    logger.debug("A business error occurred with message '{}'", ex.message)

    val message = ex.message?.let { Messages.get(it, *ex.params) } ?: "Unable to process the request"

    return ErrorDetail(
        code = ex.code,
        message = message,
        friendlyMessage = message
    )
}

/** API **/

fun handleValueInstantiationException(ex: ValueInstantiationException) : ErrorDetail {
    val cause = rootCauseOf(ex)

    if (cause is ConstraintViolationException) {
        return handleConstraintViolationException(cause)
    }

    return handleGeneralException(ex)
}

fun handleConstraintViolationException(ex: ConstraintViolationException) : ErrorDetail {
    val violations = ex.constraintViolations
        .mapToMessage(baseName = "messages/messages")
        .map { msg ->
            val fieldName = if (msg.constraint is CustomConstraint) (msg.constraint as CustomConstraint).field else msg.property.toSnakeCase()
            val params = arrayOf(fieldName, msg.value, *msg.constraint.messageParams.map { it.value.toString() }.toTypedArray())
            InputViolation(fieldName, MessageFormat.format(msg.message, *params))
        }

    return ErrorDetail(
        code = HttpStatusCode.BadRequest.value,
        message = "One or more fields/values/params/headers are invalids",
        friendlyMessage = "One or more fields/values are invalids",
        invalidFields = violations
    )
}

fun handleInvalidFormatException(ex: InvalidFormatException) : ErrorDetail {
    logger.debug("The request body has invalid values", ex)

    val field = ex.path.joinToString(separator = ".") { it.fieldName }
    val invalidValue = ex.value
    val expectedType = ex.targetType.simpleName

    return ErrorDetail(
        code = HttpStatusCode.BadRequest.value,
        message = "The value '$invalidValue' is invalid for the field '$field'. The expected type is '$expectedType'",
        friendlyMessage = "The request body has invalid values for one or more fields",
        invalidFields = listOf(InputViolation(field, "The value '$invalidValue' is invalid for the field '$field'"))
    )
}

fun handleJsonProcessingException(ex: JsonProcessingException) : ErrorDetail {
    logger.debug("The request body is invalid", ex)

    return ErrorDetail(
        code = HttpStatusCode.BadRequest.value,
        message = "The JSON request body is empty or invalid",
        friendlyMessage = "The request body is invalid"
    )
}

fun rootCauseOf(throwable: Throwable, typeOf: KClass<out Throwable>? = null): Throwable {
    var rootCause: Throwable = throwable

    while (rootCause.cause != null && rootCause !== rootCause.cause && isNotGivenType(rootCause, typeOf)) {
        rootCause = rootCause.cause!!
    }

    return rootCause
}

private fun isNotGivenType(cause: Throwable, typeOf: KClass<out Throwable>?): Boolean {
    return typeOf == null || !typeOf.java.isAssignableFrom(cause::class.java)
}
