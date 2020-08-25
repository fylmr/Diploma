package ru.fylmr.diploma.ui.chat

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ac_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.fylmr.diploma.R

class ChatActivity : MvpAppCompatActivity(), ChatView {

    private val adapter by lazy { ChatAdapter() }

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        recycler.adapter = adapter
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        manager.stackFromEnd = true
        recycler.layoutManager = manager

        sendBtn.setOnClickListener {
            presenter.sendClicked(messageEditText.text.toString())
        }
    }

    override fun addMessages(messages: List<Message>, clearField: Boolean) {
        if (clearField) {
            messageEditText.setText("")
        }

        adapter.setData(messages)
    }
}
