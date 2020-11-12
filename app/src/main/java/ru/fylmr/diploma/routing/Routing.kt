package ru.fylmr.diploma.routing

import android.net.MacAddress
import ru.fylmr.diploma.routing.MessageType.*

sealed class Message(
    val protocolVersion: Byte,
    val messageType: MessageType
) {
    /** Сигнал о нахождении в сети */
    data class HelloMessage(
        val originSender: MacAddress,
        val lastSender: MacAddress? = null,
        val hopsCount: Short = 0x0000
    ) : Message(0, HelloType)

    /** Сообщение для пересылки по сети */
    data class TextMessage(
        val originSender: MacAddress,
        val priority: Short = 0,
        val partNumber: Byte = 1,
        val partCount: Byte = 1,
        val messageSize: Short = 1,
        val messageText: String
    ) : Message(0, TextMessageType)

    /** Сообщение для финального адресата */
    data class DirectTextMessage(
        val originSender: MacAddress,
        val priority: Short = 0,
        val partNumber: Byte = 1,
        val partCount: Byte = 1,
        val messageSize: Short = 1,
        val messageText: String
    ) : Message(0, TextMessageDirectType)
}

sealed class MessageType(val representation: Short) {
    object HelloType : MessageType(0x0001)
    object TextMessageType : MessageType(0x0010)
    object TextMessageDirectType : MessageType(0x0011)
}
