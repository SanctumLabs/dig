package io.github.sanctumlabs.dig.services

import io.github.sanctumlabs.dig.core.IdGenerator
import java.util.UUID

class UUIDGenerator : IdGenerator {
    override fun generateId(): String = UUID.randomUUID().toString()
}
