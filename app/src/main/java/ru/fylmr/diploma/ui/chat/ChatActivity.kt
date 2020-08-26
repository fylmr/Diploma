package ru.fylmr.diploma.ui.chat

import android.Manifest.permission.*
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalStdlibApi
class ChatActivity : MvpAppCompatActivity(), ChatView, ChatAdapter.Listener {

    private val adapter by lazy { ChatAdapter(this) }

    private val bluetoothAdapter by lazy {
        val manager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        manager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanResults = mutableListOf<ScanResult>()
    private val bleGatts = mutableMapOf<String, BluetoothGatt>()

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

    override fun onResume() {
        super.onResume()
        if (!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }

        startBluetoothScan()
    }

    override fun onPause() {
        super.onPause()
        bleScanner.stopScan(scanCallback)
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
    //
    // ===================================================

    override fun addMessages(messages: List<Message>, clearField: Boolean) {
        if (clearField) {
            messageEditText.setText("")
        }

        adapter.setData(messages)
    }

    override fun onClick(message: Message) {
        val device = message.device ?: return
        device.connectGatt(this, false, gattCallback)
    }

    // ===================================================
    //
    // ===================================================

    private fun promptEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    private fun startBluetoothScan() {
        bleScanner.startScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
                adapter.setData(scanResults.toMessages())
            } else {
                with(result.device) {
                    Log.w(TAG, "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")
                }
                scanResults.add(result)
                adapter.setData(scanResults.toMessages())
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(TAG, "onScanFailed: code $errorCode")
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w(TAG, "Successfully connected to ${gatt.device.name ?: deviceAddress}")
                    gatt.discoverServices()

                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w(TAG, "Successfully disconnected from $deviceAddress")
                    gatt.close()
                }
            } else {
                Log.w(TAG, "Error $status encountered for $deviceAddress! Disconnecting...")
                gatt.close()
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            val address = gatt?.device?.address ?: return

            bleGatts[address] = gatt
            runOnUiThread {
                adapter.setData(scanResults.toMessages())
            }
        }
    }

    // ===================================================
    //
    // ===================================================

    private fun runPermissionChecks() {
        val permissions = arrayOf(BLUETOOTH, BLUETOOTH_ADMIN, ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
    }

    private fun getMessageFromDevice(scanResult: ScanResult): Message {
        val time = System.currentTimeMillis()
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeString = format.format(Date(time))

        val services = bleGatts[scanResult.device.address]?.services

        val table = services?.joinToString { service ->
            val characteristicsTable = service.characteristics
                .joinToString(separator = "\n|--", prefix = "|--") { it.uuid.toString() }
            "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
        }

        return Message(
            table ?: "Устройство найдено",
            scanResult.device.name ?: scanResult.device.address,
            timeString,
            scanResult.device
        )
    }

    private fun List<ScanResult>.toMessages(): List<Message> {
        return map { getMessageFromDevice(it) }.distinctBy { it.device?.address }
    }

    companion object {
        private val TAG = this::class.java.simpleName

        private const val REQUEST_PERMISSIONS_CODE = 1000
        private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1001
    }
}
