package com.orvils.config.serialization

import io.ktor.http.ContentType
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        val jsonParser = JacksonConverter(JsonParser.instance)

        register(ContentType.Any, jsonParser)
        register(ContentType.Application.Json, jsonParser)
    }
}

