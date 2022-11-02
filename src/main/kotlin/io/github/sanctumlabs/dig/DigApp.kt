package io.github.sanctumlabs.dig

import io.github.sanctumlabs.dig.infra.plugins.configureDiPlugin
import io.github.sanctumlabs.dig.infra.plugins.configureMonitoring
import io.github.sanctumlabs.dig.infra.plugins.configureRouting
import io.github.sanctumlabs.dig.infra.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

// application.conf references the main function. This annotation prevents the IDE from marking it as unused.
@Suppress("unused")
fun Application.module() {
    configureDiPlugin()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
