package io.github.sanctumlabs.dig.api

import io.github.sanctumlabs.dig.services.IdGenService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.route
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
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
