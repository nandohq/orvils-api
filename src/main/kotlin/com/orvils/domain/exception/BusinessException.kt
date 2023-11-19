package com.orvils.domain.exception

open class BusinessException(
    val code: Int, override val message: String?, val params: Array<out Any> = emptyArray()
) : RuntimeException(message)