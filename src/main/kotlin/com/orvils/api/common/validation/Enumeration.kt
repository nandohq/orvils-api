package com.orvils.api.common.validation

import com.orvils.common.extensions.describe
import com.orvils.common.message.Messages
import org.valiktor.Validator
import kotlin.reflect.KClass

data class Enumeration(val fieldName: String, val value: String?, val expectedValues: List<String>) : CustomConstraint {
    override val field: String get() = fieldName
    override val messageKey: String get() = Messages.API_INPUT_NOT_ENUMERATION
    override val messageParams: Map<String, *> = mapOf("expectedValues" to expectedValues.describe())
}

/**
 * Validates if the property value represents a valid enumeration constant.
 *
 * @param ofType The enumeration class to be used as reference.
 */
fun <E> Validator<E>.Property<String?>.isEnumeration(ofType: KClass<out Enum<*>>) : Validator<E>.Property<String?> {
    val fieldName = ValidatorHelper.getValidatedFieldName(this.obj, this.property)
    val value = this.property.get(this.obj)
    val expectedValues = ofType.java.enumConstants.map { it.name }

    return this.validate(Enumeration(fieldName, value, expectedValues)) {
        it == null || expectedValues.contains(it.uppercase())
    }
}