package com.orvils.api.controller.common.resource

import com.orvils.common.`object`.Nullable
import com.orvils.common.`object`.OptionalValued
import com.orvils.common.`object`.Present
import kotlin.reflect.full.memberProperties

interface BasePatchRequest {

    fun changedFields(): List<OptionalValued<*>> =
        this::class.memberProperties.filterIsInstance<Present<*>>()
            .plus(this::class.memberProperties.filterIsInstance<Nullable>())
}