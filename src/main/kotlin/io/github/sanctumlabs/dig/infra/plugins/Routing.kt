package io.github.sanctumlabs.dig.infra.plugins

import io.github.sanctumlabs.dig.api.digRestApi
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        digRestApi()
    }
}
