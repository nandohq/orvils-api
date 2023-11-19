package com.orvils.common.extensions

fun List<Any>.describe(lastSeparator: LastSeparator = LastSeparator.AND): String = this.toTypedArray().describe(lastSeparator)



