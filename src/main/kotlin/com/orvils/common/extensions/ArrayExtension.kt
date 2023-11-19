package com.orvils.common.extensions

enum class LastSeparator(val value: String) { NONE(""), OR(" or "), AND(" and ") }

fun Array<out Any>.describe(lastSeparator: LastSeparator = LastSeparator.AND): String {
    if (LastSeparator.NONE == lastSeparator) {
        return this.joinToString(separator = ", ") { "'$it'" }
    }

    return this.joinToString(separator = "") {
        if (this.size == 1) "'$it'"
        else
            when(it) {
                this.penultimate() -> "'$it'"
                this.last() -> " ${lastSeparator.value} '$it'"
                else -> "'$it', "
            }
    }
}

fun Array<out Any>.penultimate(): Any {
    if (this.isEmpty()) {
        throw NoSuchElementException("Array is empty.")
    }

    if (this.size == 1) {
        throw NoSuchElementException("Array has only one element.")
    }

    return this[this.size - 2]
}