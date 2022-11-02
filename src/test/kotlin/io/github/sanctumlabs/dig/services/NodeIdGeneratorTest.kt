package io.github.sanctumlabs.dig.services

import kotlin.test.assertNotEquals
import kotlin.test.*

internal class NodeIdGeneratorTest {
    @Test
    @Ignore("Will always generate the same node id on the same machine this test is running on")
    fun `should always generate a new random node id`() {
        val nodeIdOne = generateNodeId()
        val nodeIdTwo = generateNodeId()

        assertNotEquals(nodeIdOne, nodeIdTwo)
    }
}
