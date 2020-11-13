package ru.fylmr.diploma.routing

import android.net.MacAddress
import ru.fylmr.diploma.routing.Message.*
import ru.fylmr.diploma.routing.MessageType.*
import java.io.Serializable
import java.util.*

sealed class Message(
    val protocolVersion: Byte,
    val messageType: MessageType,
) : Serializable, Comparable<Message> {
    abstract val priority: Short

    override fun compareTo(other: Message): Int {
        return priority.compareTo(other.priority)
    }

    /** Сигнал о нахождении в сети */
    data class HelloMessage(
        val originSender: MacAddress,
        val lastSender: MacAddress? = null,
        val hopsCount: Short = 0x0000,
        override val priority: Short = 5
    ) : Message(0, HelloType)

    /** Сообщение для пересылки по сети */
    data class TextMessage(
        val originSender: MacAddress,
        val destination: MacAddress,
        override val priority: Short = 0,
        val partNumber: Byte = 1,
        val partCount: Byte = 1,
        val messageSize: Short = 1,
        val messageText: String
    ) : Message(0, TextMessageType)

    /** Сообщение для финального адресата */
    data class DirectTextMessage(
        val originSender: MacAddress,
        val destination: MacAddress,
        override val priority: Short = 0,
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

class Routes {
    private val sorted = TreeSet<Route>()

    fun add(r: Route) {
        val old = sorted.find { it.connection == r.connection }
        if (old == null || old.hopsCount <= r.hopsCount) {
            return
        }

        sorted.remove(old)
        sorted.add(r)
    }

    fun get(): List<Route> {
        return sorted.toList()
    }
}

data class Route(
    val connection: MacAddress?,
    val hopsCount: Short
) : Comparable<Route> {
    override fun compareTo(other: Route): Int {
        return hopsCount.compareTo(other.hopsCount)
    }
}

private val queue = PriorityQueue<Message>(1000)
private val routes = mutableMapOf<MacAddress, Routes>()

fun handle(message: Message): Any? = when (message) {
    is HelloMessage -> {
        val route = Route(message.lastSender, message.hopsCount)
        if (routes[message.originSender] == null) {
            routes[message.originSender] = Routes()
        }
        routes[message.originSender]?.add(route)
        // Добавить в список маршрутов
        // и передать сообщение дальше
    }
    is TextMessage -> {
        val messages = routes[message.destination]?.get()?.mapIndexed { i, r ->
            val newPriority = message.priority + i
            message.copy(priority = newPriority.toShort())
        }
        // Отправить сообщение дальше
    }
    is DirectTextMessage -> {
        print(message.messageText)
        // Сообщение этому узлу — прочитаем
    }
}

fun loop() {
    while (true) {
        if (queue.isNotEmpty()) {
            val m = queue.lastOrNull() ?: return
            handle(m)
        }
    }
}
