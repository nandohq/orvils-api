package com.orvils.api.common.validation

import com.orvils.common.message.Messages
import org.valiktor.Validator

data class Number(val fieldName: String) : CustomConstraint {
    override val field: String get() = fieldName
    override val messageKey: String get() = Messages.API_INPUT_NOT_NUMBER
}

/**
 * Validates if the property is a number.
 * By default, only positive numbers are valids.
 *
 * @param onlyPositive If true, only positive numbers are valids
 */
fun <E> Validator<E>.Property<String?>.isNumber(onlyPositive: Boolean = false) : Validator<E>.Property<String?> {
    val fieldName = ValidatorHelper.getValidatedFieldName(this.obj, this.property)

    return this.validate(Number(fieldName)) {
        it == null || (it.matches(Regex("^[0-9]*\$")) && (!onlyPositive || it.toLong() > 0))
    }
}