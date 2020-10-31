package ru.fylmr.diploma.bluetooth

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.fylmr.diploma.bluetoothchat.Constants.MESSAGE_READ
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class BluetoothDelegate(private val adapter: BluetoothAdapter) {

    private var onNewDevice: (device: BluetoothDevice?) -> Unit = {}

    fun getBonded(): Set<BluetoothDevice> = adapter.bondedDevices

    fun startScanning(context: Activity, onNewDevice: (device: BluetoothDevice?) -> Unit) {
        if (!accessLocationGranted(context)) {
            requestLocation(context)
            return
        }

        this.onNewDevice = onNewDevice

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(scanReceiver, filter)
    }

    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action

            if (action == BluetoothDevice.ACTION_FOUND) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                onNewDevice(device)
            }
        }
    }


    // ===================================================
    // Permissions
    // ===================================================

    private fun accessLocationGranted(context: Context) =
        ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) ==
                PERMISSION_GRANTED

    fun requestLocation(context: Activity) {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(ACCESS_COARSE_LOCATION),
            COARSE_LOCATION_REQUEST_CODE
        )
    }

    // ===================================================
    //
    // ===================================================

    fun onStop(context: Context) {
        context.unregisterReceiver(scanReceiver)
    }

    companion object {
        const val COARSE_LOCATION_REQUEST_CODE = 1005

        private const val NAME = "BluetoothChat"
        private val MY_UUID = UUID.fromString("BluetoothChatUuid")
    }

    private class ConnectedThread(
        private val handler: Handler,
        private val socket: BluetoothSocket,
    ) : Thread() {

        private val inStream: InputStream? = socket.inputStream
        private val outStream: OutputStream? = socket.outputStream

        override fun run() {
            val buffer = ByteArray(1024) // буферный массив

            // Прослушиваем InputStream, пока не произойдет исключение
            while (true) {
                try {
                    // читаем из InputStream
                    val bytes = inStream?.read(buffer) ?: return

                    // посылаем прочитанные байты в главный поток
                    handler
                        .obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                        .sendToTarget()
                } catch (e: IOException) {
                    break
                }
            }
        }

        /**
         * Вызываем этот метод из главного потока, чтобы передать данные
         */
        fun write(bytes: ByteArray) {
            try {
                outStream?.write(bytes)
            } catch (e: IOException) {
                // Покажем ошибку пользователю
            }
        }

        /**
         * Вызываем этот метод из главного потока, чтобы разорвать соединение
         */
        fun cancel() {
            try {
                socket.close()
            } catch (e: IOException) {
                // Покажем ошибку пользователю
            }
        }

    }
}
