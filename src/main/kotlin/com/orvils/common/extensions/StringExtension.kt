package com.orvils.common.extensions

import com.fasterxml.jackson.databind.PropertyNamingStrategies

fun String.toSnakeCase(): String = PropertyNamingStrategies.SnakeCaseStrategy().translate(this)

fun String.toCamelCase(): String = PropertyNamingStrategies.LowerCamelCaseStrategy().translate(this)