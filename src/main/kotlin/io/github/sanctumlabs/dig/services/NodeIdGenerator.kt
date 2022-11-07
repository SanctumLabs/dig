package io.github.sanctumlabs.dig.services

import io.github.sanctumlabs.dig.infra.config.NODE_ID_BIT_LEN
import java.net.NetworkInterface
import java.net.SocketException
import java.security.SecureRandom
import kotlin.math.pow

fun generateNodeId(): Int {
    val maxNodeVal = 2.0.pow(NODE_ID_BIT_LEN).toInt()
    val nodeId = try {
        val sb = StringBuilder()
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val networkInterface = networkInterfaces.nextElement()
            val mac = networkInterface.hardwareAddress
            if (mac != null) {
                for (element in mac) {
                    sb.append(String.format("%02X", element))
                }
            }
        }
        sb.toString().hashCode()
    } catch (se: SocketException) {
        SecureRandom().nextInt() % 2.0.pow(10).toInt()
    }
    return nodeId and maxNodeVal
}
