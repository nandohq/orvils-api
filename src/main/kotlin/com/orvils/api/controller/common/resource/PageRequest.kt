package com.orvils.api.controller.common.resource

import com.orvils.api.common.validation.isEnumeration
import com.orvils.infrastructure.repository.resource.pagination.Direction
import com.orvils.infrastructure.repository.resource.pagination.Order
import com.orvils.infrastructure.repository.resource.pagination.Pageable
//import kotlinx.serialization.Serializable
import org.valiktor.validate

//@Serializable
data class PageRequest(
    val page: Int = 0,
    val size: Int = 10,
    val sort: List<SortRequest> = listOf(SortRequest("id"))
)

//@Serializable
data class SortRequest(
    val field: String,
    val direction: String = "ASC"
) {
    init {
        validate(this) {
            validate(SortRequest::direction).isEnumeration(ofType = Direction::class)
        }
    }
}

fun PageRequest.toPageable() = Pageable(
    page = page,
    size = size,
    sort = sort.map { Order(it.field, Direction.valueOf(it.direction.uppercase())) }
)