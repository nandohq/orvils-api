package com.orvils.domain.model

import java.util.UUID
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

abstract class BaseModel(uuid: UUID? = null) {
    val id: UUID
    @Transient val isNew: Boolean

    init {
        id = if (uuid == null) {
            isNew = true
            UUID.randomUUID()
        } else {
            isNew = false
            uuid
        }
    }

    @Suppress("unchecked_cast")
    fun fields() : Map<String, Any?> {
        return this::class.declaredMemberProperties.map { it as KProperty1<BaseModel, Any?> }.associate { it.name to it.get(this) }
    }
}

