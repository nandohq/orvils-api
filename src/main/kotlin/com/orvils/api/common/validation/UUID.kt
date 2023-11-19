package com.orvils.api.common.validation

import com.orvils.common.message.Messages
import org.valiktor.Validator

data class UUID(val fieldName: String) : CustomConstraint {
    override val field: String get() = fieldName
    override val messageKey: String get() = Messages.API_INPUT_NOT_UUID
}

/**
 * Validates if a property is a valid UUID.
 */
fun <E> Validator<E>.Property<String?>.isUUID() : Validator<E>.Property<String?> {
    val fieldName = ValidatorHelper.getValidatedFieldName(this.obj, this.property)
    return this.validate(UUID(fieldName)) {
        it == null || try {
            java.util.UUID.fromString(it)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}