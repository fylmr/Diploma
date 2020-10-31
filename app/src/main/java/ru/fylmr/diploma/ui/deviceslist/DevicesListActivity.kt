package ru.fylmr.diploma.ui.deviceslist

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.ac_devices_list.*
import ru.fylmr.diploma.R
import ru.fylmr.diploma.bluetooth.BluetoothDelegate

@ExperimentalStdlibApi
class DevicesListActivity : AppCompatActivity(R.layout.ac_devices_list) {

    private val adapter by lazy { DevicesListAdapter() }

    private val delegate by lazy { BluetoothDelegate(BluetoothAdapter.getDefaultAdapter()) }

    private val showedAddresses = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        showList()
    }

    override fun onStart() {
        super.onStart()

        startScanning()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == BluetoothDelegate.COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults[0] != PERMISSION_GRANTED) {
                delegate.requestLocation(this)
            } else {
                startScanning()
            }
        }
    }

    override fun onStop() {
        super.onStop()

        delegate.onStop(this)
    }

    private fun showList() {
        val items = buildList<DeviceListItem> {
            this += DeviceListItem.Title("Спаренные устройства:")
            this += showBonded()

            this += DeviceListItem.Title("В зоне видимости:")
        }

        adapter.setData(items)
    }

    private fun showBonded(): List<DeviceListItem.DeviceItem> {
        val bonded = delegate.getBonded()

        showedAddresses.addAll(bonded.map { it.address })

        return bonded.map { DeviceListItem.DeviceItem(it) }
    }


    private fun startScanning() {
        delegate.startScanning(this, ::onNewDeviceFound)
    }

    private fun onNewDeviceFound(device: BluetoothDevice?) {
        device ?: return

        if (device.address !in showedAddresses) {
            adapter.addData(DeviceListItem.DeviceItem(device))
        }
    }
}
