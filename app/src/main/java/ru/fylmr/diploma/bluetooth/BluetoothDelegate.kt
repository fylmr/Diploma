package ru.fylmr.diploma.bluetooth

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
    }
}
