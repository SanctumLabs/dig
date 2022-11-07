package io.github.sanctumlabs.dig.core

import io.github.sanctumlabs.dig.exceptions.ClockMovedBackException
import io.github.sanctumlabs.dig.exceptions.NodeIdOutOfBoundsException
import kotlin.jvm.Throws

interface SequenceIdGenerator {
    @Throws(ClockMovedBackException::class, NodeIdOutOfBoundsException::class)
    fun generateId(): Long
}
