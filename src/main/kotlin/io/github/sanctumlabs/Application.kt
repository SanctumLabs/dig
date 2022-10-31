package io.github.sanctumlabs

import io.ktor.server.application.*
import io.github.sanctumlabs.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
