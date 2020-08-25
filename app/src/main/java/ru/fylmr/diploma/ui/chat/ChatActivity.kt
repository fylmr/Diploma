package ru.fylmr.diploma.ui.chat

import android.Manifest.permission.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ac_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.fylmr.diploma.R

@ExperimentalStdlibApi
class ChatActivity : MvpAppCompatActivity(), ChatView {

    private val adapter by lazy { ChatAdapter() }

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)

        runPermissionChecks()

        recycler.adapter = adapter
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        manager.stackFromEnd = true
        recycler.layoutManager = manager

        sendBtn.setOnClickListener {
            presenter.sendClicked(messageEditText.text.toString())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUEST_PERMISSIONS_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.any { it != PERMISSION_GRANTED }) {
            Toast.makeText(this, "Разрешений недостаточно!", Toast.LENGTH_LONG).show()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // ===================================================
    //
    // ===================================================

    override fun addMessages(messages: List<Message>, clearField: Boolean) {
        if (clearField) {
            messageEditText.setText("")
        }

        adapter.setData(messages)
    }

    // ===================================================
    //
    // ===================================================

    private fun runPermissionChecks() {
        val permissions = arrayOf(BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
    }

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1000
    }
}
