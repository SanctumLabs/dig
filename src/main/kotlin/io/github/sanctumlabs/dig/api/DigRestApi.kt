package io.github.sanctumlabs.dig.api

import io.github.sanctumlabs.dig.services.IdGenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.digRestApi() {
    val idGenService: IdGenService by inject()

    route("/dig") {

        get {
            runCatching { idGenService.generateId() }
                .mapCatching {
                    call.respond(status = HttpStatusCode.OK, message = IdResponseDto(id = it))
                }
                .getOrElse {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = mapOf("message" to "Failed to generate ID")
                    )
                }
        }
    }
}
