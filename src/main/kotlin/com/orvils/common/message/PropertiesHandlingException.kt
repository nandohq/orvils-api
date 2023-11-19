package com.orvils.common.message

class PropertiesHandlingException(
    override val message: String?,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)