package com.orvils.api.controller.publisher

//import com.orvils.config.serialization.serializer.UUIDSerializer
import com.orvils.domain.model.Publisher
//import kotlinx.serialization.Contextual
//import kotlinx.serialization.Serializable
import java.util.UUID

//@Serializable
data class SimplePublisherResponse(
//    @Contextual
//    @Serializable(with = UUIDSerializer::class)
    val id: UUID?,
    val name: String
) {
    constructor(publisher: Publisher) : this(publisher.id, publisher.name)
}
