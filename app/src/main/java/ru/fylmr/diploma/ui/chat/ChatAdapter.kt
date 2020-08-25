package ru.fylmr.diploma.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.li_chat_message.view.*
import ru.fylmr.diploma.R

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatVH>() {

    private val data = mutableListOf<Message>()

    fun setData(data: List<Message>) {
        this.data.clear()
        this.data.addAll(data)

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

    class ChatVH(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(message: Message) = itemView.apply {
            messageText.text = message.text
            authorName.text = message.authorName
            messageTime.text = message.time
        }
    }
}

data class Message(
    val text: String,
    val authorName: String,
    val time: String
)
