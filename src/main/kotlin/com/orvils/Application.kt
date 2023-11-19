package com.orvils

import com.orvils.config.*
import com.orvils.config.error.configureErrorHandling
import com.orvils.config.injection.BeansModule
import com.orvils.config.serialization.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.requestvalidation.*
import org.koin.core.context.startKoin
import org.koin.ktor.plugin.Koin
import org.koin.ksp.generated.module

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    DBManager.configureDatabases()

    startKoin {
        modules(BeansModule().module)
    }

    configureOpenApi()
    configureRouting()
    configureSerialization()
    configureErrorHandling()

    install(Koin)
    install(RequestValidation)
}
