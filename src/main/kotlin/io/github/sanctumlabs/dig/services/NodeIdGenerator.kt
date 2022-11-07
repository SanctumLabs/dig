package io.github.sanctumlabs.dig.services

import io.github.sanctumlabs.dig.infra.config.NODE_ID_BIT_LEN
import java.net.NetworkInterface
import java.security.SecureRandom
import java.util.*
import kotlin.math.pow

fun generateNodeId(): Int {
    val maxNodeVal = 2.0.pow(NODE_ID_BIT_LEN).toInt()
    val nodeId = runCatching {
        val sb = StringBuilder()
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            networkInterface.hardwareAddress?.forEach {
                sb.append(String.format(Locale.getDefault(), "%02X", it))
            }
        }
        sb.toString().hashCode()
    }
        .getOrElse { SecureRandom().nextInt() % 2.0.pow(NODE_ID_BIT_LEN).toInt() }
    return nodeId and maxNodeVal
}
