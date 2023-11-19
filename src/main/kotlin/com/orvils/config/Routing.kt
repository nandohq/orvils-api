package com.orvils.config

import com.orvils.api.route.bookRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        bookRouting()
    }
}
