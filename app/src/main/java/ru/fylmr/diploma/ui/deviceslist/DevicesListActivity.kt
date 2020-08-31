package ru.fylmr.diploma.ui.deviceslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.ac_devices_list.*
import ru.fylmr.diploma.R

class DevicesListActivity : AppCompatActivity(R.layout.ac_devices_list) {

    private val adapter by lazy { DevicesListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }
}
