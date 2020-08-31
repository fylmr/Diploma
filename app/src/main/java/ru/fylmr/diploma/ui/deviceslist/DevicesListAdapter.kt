package ru.fylmr.diploma.ui.deviceslist

import android.view.View
import ru.fylmr.diploma.R
import ru.fylmr.diploma.ui.base.BaseAdapter
import ru.fylmr.diploma.ui.base.BaseVH

class DevicesListAdapter : BaseAdapter<DeviceListItem, DevicesListAdapter.DevicesListVH>() {

    override fun getLayout(viewType: Int): Int {
        return R.layout.li_device
    }

    override fun getViewHolder(view: View, viewType: Int): DevicesListVH {
        return DeviceVH(view)
    }

    abstract class DevicesListVH(v: View) : BaseVH<DeviceListItem>(v)

    class DeviceVH(v: View) : DevicesListVH(v) {
        override fun bind(data: DeviceListItem) {
            // todo
        }
    }
}

sealed class DeviceListItem {
    object S : DeviceListItem()
}
