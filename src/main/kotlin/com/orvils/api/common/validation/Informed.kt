package com.orvils.api.common.validation

import com.orvils.common.message.Messages
import org.valiktor.Validator

data class Informed(val fieldName: String, val value: String?) : CustomConstraint {
    override val field: String get() = fieldName
    override val messageKey: String get() = Messages.API_INPUT_NOT_INFORMED
}

/**
 * Validates if a required property has been informed.
 * By default, null or blank values are not valids.
 *
 * @param allowBlank If true, blank values are valids
 */
fun <E> Validator<E>.Property<String?>.hasBeenInformed(allowBlank: Boolean = false) : Validator<E>.Property<String?> {
    val fieldName = ValidatorHelper.getValidatedFieldName(this.obj, this.property)
    val value = this.property.get(this.obj)

    return this.validate(Informed(fieldName, value)) {
        it != null && (allowBlank || it.isNotBlank())
    }
}