package io.github.sanctumlabs.dig.services

import io.github.sanctumlabs.dig.core.IdGenerator
import io.github.sanctumlabs.dig.core.SequenceIdGenerator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IdGenService : KoinComponent {
    private val sequenceIdGenerator: SequenceIdGenerator by inject()
    private val uuidGenerator: IdGenerator by inject()

    fun generateId(): String = runCatching { sequenceIdGenerator.generateId() }
        .mapCatching { it.toString() }
        .getOrElse { uuidGenerator.generateId() }
}
