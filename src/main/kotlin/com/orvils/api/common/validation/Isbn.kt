package com.orvils.api.common.validation

import com.orvils.common.message.Messages
import org.valiktor.Validator

private val isbn13Regex = Regex("^[0-9]{13}$")
private val isbn10Regex = Regex("^[0-9]{9}[0-9X]$")

data class Isbn(val fieldName: String) : CustomConstraint {
    override val field: String get() = fieldName
    override val messageKey: String get() = Messages.API_INPUT_INVALID_ISBN
}

/**
 * Validates if a property is a valid ISBN.
 * This validation consider the both ISBN patterns (with 10 and 13 digits).
 */
fun <E> Validator<E>.Property<String?>.isIsbn() : Validator<E>.Property<String?> {
    val fieldName = ValidatorHelper.getValidatedFieldName(this.obj, this.property)
    return this.validate(Isbn(fieldName)) { it == null || validate(it) }
}

private fun validate(value: String) : Boolean {
    return when (value.length) {
        10 -> validateIsbn10(value)
        13 -> validateIsbn13(value)
        else -> false
    }
}

fun validateIsbn13(value: String): Boolean {
    if (!isbn13Regex.matches(value)) return false

    var sum = 0
    val digits = value.toCharArray()

    for (i in 0..12) {
        val digit = digits[i] - '0'
        sum += if (i % 2 == 0) digit else digit * 3
    }

    return sum % 10 == 0
}

fun validateIsbn10(value: String): Boolean {
    if (!isbn10Regex.matches(value)) return false

    var sum = 0
    val digits = value.toCharArray()

    for (i in 0..  8) {
        val digit = digits[i]
        if (isOutOfRange(digit)) return false
        sum += (digit - '0') * (10 - i)
    }

    val lastDigit = digits.last()
    val isX = lastDigit.equals('X', true)

    if (!isX && isOutOfRange(lastDigit)) return false

    sum += if (isX) 10 else lastDigit - '0'

    return sum % 11 == 0
}

private fun isOutOfRange(digit: Char) : Boolean {
    return digit < '0' || digit > '9'
}

