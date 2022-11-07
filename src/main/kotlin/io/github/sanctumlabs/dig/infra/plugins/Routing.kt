package io.github.sanctumlabs.dig.infra.plugins

import io.github.sanctumlabs.dig.api.digRestApi
import io.ktor.server.routing.routing
import io.ktor.server.application.Application

fun Application.configureRouting() {
    routing {
        digRestApi()
    }
}
