package ru.fylmr.diploma.ui.chat

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ac_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.fylmr.diploma.R
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalStdlibApi
class ChatActivity : MvpAppCompatActivity(R.layout.ac_main), ChatView, ChatAdapter.Listener {

    private val adapter by lazy { ChatAdapter(this) }

    private val bluetoothAdapter by lazy {
        BluetoothAdapter.getDefaultAdapter()
    }

    @InjectPresenter
    lateinit var presenter: ChatPresenter

    // ===================================================
    // Лайфсайкл
    // ===================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runPermissionChecks()

        recycler.adapter = adapter
        val manager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        manager.stackFromEnd = true
        recycler.layoutManager = manager

        sendBtn.setOnClickListener {
            presenter.sendClicked(messageEditText.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        if (!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }

        startBluetoothScan()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_PERMISSIONS_CODE -> {
                if (grantResults.any { it != PERMISSION_GRANTED }) {
                    Toast.makeText(this, "Разрешений недостаточно!", Toast.LENGTH_LONG).show()
                }
            }
            ENABLE_BLUETOOTH_REQUEST_CODE -> {
                if (grantResults.firstOrNull() != Activity.RESULT_OK) {
                    promptEnableBluetooth()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    // ===================================================
    // Список сообщений
    // ===================================================

    override fun addMessages(messages: List<Message>, clearField: Boolean) {
        if (clearField) {
            messageEditText.setText("")
        }

        adapter.setData(messages)
    }

    override fun onClick(message: Message) {
        val device = message.device ?: return
        // todo
    }

    // ===================================================
    // Блютус
    // ===================================================

    private fun promptEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    @SuppressLint("HardwareIds")
    private fun startBluetoothScan() {
        addUserLog("Включён Блютус.\n" +
                "Имя: ${bluetoothAdapter.name}.\n" +
                "Начинаем поиск...")

        // Сначала покажем уже присоединённые устройства
        val paired = bluetoothAdapter.bondedDevices
        paired.forEach {
            addUserLog("Известное устрйство: ${it.name} (${it.address})", it)
        }
    }

    // ===================================================
    //
    // ===================================================

    private fun runPermissionChecks() {
        val permissions = arrayOf(BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
    }

    private fun addUserLog(message: String, bluetoothDevice: BluetoothDevice? = null) {
        val time = System.currentTimeMillis()
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = format.format(Date(time))

        val chatMessage = Message(message, "Информация", timeString, bluetoothDevice)
        adapter.addData(chatMessage)
    }

    companion object {
        private val TAG = this::class.java.simpleName

        private const val REQUEST_PERMISSIONS_CODE = 1000
        private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1001
    }
}
