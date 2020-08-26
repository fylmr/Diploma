package ru.fylmr.diploma.ui.chat

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_chat_message.view.*
import ru.fylmr.diploma.R

class ChatAdapter(private val listener: Listener) : RecyclerView.Adapter<ChatAdapter.ChatVH>() {

    private val data = mutableListOf<Message>()

    fun setData(data: List<Message>) {
        this.data.clear()
        this.data.addAll(data)

        notifyDataSetChanged()
    }

    fun addData(data: Message, position: Int? = null) {
        if (position == null) {
            this.data.add(data)
        } else {
            this.data.add(position, data)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatVH {
        return ChatVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.li_chat_message, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatVH, position: Int) {
        holder.bind(data[position])
    }

    inner class ChatVH(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(message: Message) = itemView.apply {
            messageText.text = message.text
            authorName.text = message.authorName
            messageTime.text = message.time

            setOnClickListener { listener.onClick(message) }
        }
    }

    interface Listener {
        fun onClick(message: Message)
    }
}

data class Message(
    val text: String,
    val authorName: String,
    val time: String,
    val device: BluetoothDevice?
)
