package io.github.sanctumlabs.dig.infra.plugins

import io.github.sanctumlabs.dig.infra.di.idGenServiceModule
import io.github.sanctumlabs.dig.infra.di.idGeneratorModule
import io.github.sanctumlabs.dig.infra.di.sequenceGeneratorModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDiPlugin() {
    install(Koin) {
        slf4jLogger()
        modules(idGeneratorModule, sequenceGeneratorModule, idGenServiceModule)
    }
}
