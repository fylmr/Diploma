package ru.fylmr.diploma.ui.deviceslist

import android.bluetooth.BluetoothDevice
import android.view.View
import kotlinx.android.synthetic.main.li_device.view.*
import kotlinx.android.synthetic.main.li_device_list_title.view.*
import ru.fylmr.diploma.R
import ru.fylmr.diploma.ui.base.BaseAdapter
import ru.fylmr.diploma.ui.base.BaseVH

class DevicesListAdapter : BaseAdapter<DeviceListItem, DevicesListAdapter.DevicesListVH>() {

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is DeviceListItem.Title -> R.layout.li_device_list_title
        is DeviceListItem.DeviceItem -> R.layout.li_device
    }

    override fun getLayout(viewType: Int) = viewType

    override fun getViewHolder(view: View, viewType: Int) = when (viewType) {
        R.layout.li_device_list_title -> TitleVH(view)
        R.layout.li_device -> DeviceVH(view)
        else -> throw IllegalStateException("Неверный viewType: $viewType")
    }

    abstract class DevicesListVH(v: View) : BaseVH<DeviceListItem>(v)

    class TitleVH(v: View) : DevicesListVH(v) {
        override fun View.bind(data: DeviceListItem) {
            data as DeviceListItem.Title

            deviceListTitle.text = data.title
        }
    }

    class DeviceVH(v: View) : DevicesListVH(v) {
        override fun View.bind(data: DeviceListItem) {
            data as DeviceListItem.DeviceItem

            val name = data.bluetoothDevice.name
            val address = data.bluetoothDevice.address

            val title = if (name.isNullOrBlank()) {
                address
            } else {
                "$name ($address)"
            }
            deviceName.text = title
        }
    }
}

sealed class DeviceListItem {
    data class Title(val title: String) : DeviceListItem()
    data class DeviceItem(val bluetoothDevice: BluetoothDevice) : DeviceListItem()
}
