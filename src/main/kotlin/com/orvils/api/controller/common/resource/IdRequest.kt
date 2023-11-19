package com.orvils.api.controller.common.resource

import com.orvils.api.common.validation.hasBeenInformed
import com.orvils.api.common.validation.isUUID
//import kotlinx.serialization.Serializable
import org.valiktor.validate

//@Serializable
class IdRequest(val id: String? = null) {

    init {
        validate(this) {
            validate(IdRequest::id).hasBeenInformed()
            validate(IdRequest::id).isUUID()
        }
    }

    fun asUUID(): java.util.UUID = java.util.UUID.fromString(id)
}