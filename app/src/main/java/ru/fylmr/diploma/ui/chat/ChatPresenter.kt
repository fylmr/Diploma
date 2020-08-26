package ru.fylmr.diploma.ui.chat

import moxy.InjectViewState
import moxy.MvpPresenter
import java.text.SimpleDateFormat
import java.util.*

@InjectViewState
class ChatPresenter : MvpPresenter<ChatView>() {
    private val messages = mutableListOf<Message>()

    fun sendClicked(message: String) {
        val time = System.currentTimeMillis()
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = format.format(Date(time))

        messages.add(Message(message, "Я", timeString, null))

        viewState.addMessages(messages, true)
    }
}
