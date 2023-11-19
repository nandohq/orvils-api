package com.orvils.common.`object`

import org.valiktor.Validator
import org.valiktor.validate

sealed interface OptionalValued<out T : Any> {
    val field: String
    val value: T?
}

data class Present<T : Any>(override val field: String, override val value: T) : OptionalValued<T> {
    override fun toString(): String = value.toString()
}

data object Absent : OptionalValued<Nothing> {
    override val field: String = ""
    override val value: Nothing? = null
}

data class Nullable(override val field: String) : OptionalValued<Nothing> {
    override val value: Nothing? = null
}

inline fun <T : Any> OptionalValued<T>.checkIf(validator: Validator<Present<T>>.Property<T?>.() -> Validator<Present<T>>.Property<T?>) {
    if (this !is Present) return

    validate(this) {
        validate(Present<T>::value).apply {
            validator.invoke(this)
        }
    }
}

