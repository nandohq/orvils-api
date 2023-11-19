package com.orvils.api.controller.common.resource

//import com.orvils.config.serialization.serializer.UUIDSerializer
//import kotlinx.serialization.Contextual
//import kotlinx.serialization.Serializable
import java.util.*

//@Serializable
data class SimpleEntityResponse(
//    @Contextual
//    @Serializable(with = UUIDSerializer::class)
    val id: UUID? = null,
    val name: String? = null
)
