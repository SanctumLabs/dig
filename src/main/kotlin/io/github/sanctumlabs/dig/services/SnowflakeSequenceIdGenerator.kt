package io.github.sanctumlabs.dig.services

import io.github.sanctumlabs.dig.core.SequenceIdGenerator
import io.github.sanctumlabs.dig.exceptions.ClockMovedBackException
import io.github.sanctumlabs.dig.exceptions.NodeIdOutOfBoundsException
import io.github.sanctumlabs.dig.infra.config.NODE_ID_BIT_LEN
import io.github.sanctumlabs.dig.infra.config.SEQUENCE_BIT_LEN
import java.time.Instant
import kotlin.math.pow


class SnowflakeSequenceIdGenerator : SequenceIdGenerator {
    private val maxSequence = 2.0.pow(SEQUENCE_BIT_LEN).toInt()
    private val maxNodeVal = 2.0.pow(NODE_ID_BIT_LEN).toInt()
    private val epochStart = Instant.EPOCH.toEpochMilli()

    @Volatile
    private var currentSequence = -1L

    @Volatile
    private var lastTimestamp = -1L
    private val lock = Any()

    private val nodeId: Int = generateNodeId()

    init {
        checkNodeIdBounds()
    }

    @Throws(NodeIdOutOfBoundsException::class)
    fun checkNodeIdBounds() {
        require(nodeId > 0 || nodeId < maxNodeVal) {
            throw NodeIdOutOfBoundsException("NodeId is < 0 or > $maxNodeVal")
        }
    }

    private fun getTimestamp(): Long = Instant.now().toEpochMilli() - epochStart

    private fun waitNextMillis(currentTimestamp: Long): Long {
        var currentTimeStamp = currentTimestamp
        while (currentTimestamp == lastTimestamp) {
            currentTimeStamp = getTimestamp()
        }
        return currentTimeStamp
    }

    override fun generateId(): Long {
        checkNodeIdBounds()
        synchronized(lock) {
            var currentTimeStamp: Long = getTimestamp()
            if (currentTimeStamp < lastTimestamp) {
                throw ClockMovedBackException("Clock moved back")
            }
            if (currentTimeStamp == lastTimestamp) {
                currentSequence = currentSequence + 1 and maxSequence.toLong()
                if (currentSequence != 0L) {
                    currentTimeStamp = waitNextMillis(currentTimeStamp)
                }
            } else {
                currentSequence = 0
            }
            lastTimestamp = currentTimeStamp
            var id = currentTimeStamp shl NODE_ID_BIT_LEN + SEQUENCE_BIT_LEN
            id = id or (nodeId shl SEQUENCE_BIT_LEN).toLong()
            id = id or currentSequence
            return id
        }
    }
}
