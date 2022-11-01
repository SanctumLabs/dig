package io.github.sanctumlabs.dig.infra.plugins

import io.github.sanctumlabs.dig.core.IdGenerator
import io.github.sanctumlabs.dig.core.SequenceIdGenerator
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val sequenceIdGenerator: SequenceIdGenerator by inject()
    val uuidGenerator: IdGenerator by inject()

    routing {
        get("/dig") {
            val id = runCatching { sequenceIdGenerator.generateId() }
                .getOrElse { uuidGenerator.generateId() }

            call.respond(HttpStatusCode.OK, id)
        }
    }
}
