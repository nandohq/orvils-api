package com.orvils.common.extensions

import com.orvils.api.controller.common.resource.IdRequest
import com.orvils.api.controller.common.resource.PageRequest
import com.orvils.api.controller.common.resource.SortRequest
import com.orvils.common.mapper.CommonMapper
import io.ktor.server.application.*
import kotlin.reflect.KClass

fun ApplicationCall.id(paramName: String = "id") : IdRequest {
    return IdRequest(parameters[paramName])
}

fun ApplicationCall.page() : PageRequest {
    val page = this.parameters["page"]?.toIntOrNull() ?: 0
    val size = this.parameters["size"]?.toIntOrNull() ?: 10
    val sorts = this.parameters.getAll("sort") ?: listOf("id")

    val sortsRequest = sorts.map {
        val sort = it.split(",")
        SortRequest(sort[0], sort.getOrNull(1) ?: "ASC")
    }

    return PageRequest(page, size, sortsRequest)
}

fun <T: Any> ApplicationCall.typedQueryParams(withType: KClass<T>) : T {
    val fieldsMap: Map<String, Any?> = this.request.queryParameters
        .entries()
        .associate { it.key to defineValue(it.value) }

    return CommonMapper.convertTo(fieldsMap, withType)
}

private fun defineValue(values: List<String>) : Any? {
    if (values.isEmpty()) return null

    val splitted = values.first().split(",").map { it.trim() }
    return if (splitted.size > 1) splitted else splitted.first()
}