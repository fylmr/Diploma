package ru.fylmr.diploma.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Data, VH : BaseVH<Data>> : RecyclerView.Adapter<VH>() {

    private val data = mutableListOf<Data>()

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        getViewHolder(getView(parent, viewType), viewType)

    /**
     * Должен отдавать лэйаут в форме R.layout.X
     */
    @LayoutRes
    abstract fun getLayout(viewType: Int): Int

    /**
     * Должен отдавать нужный [BaseVH] в зависимости от [viewType]
     */
    abstract fun getViewHolder(view: View, viewType: Int): VH

    /**
     * Можно переопределить для передачи листенеров. По умолчанию биндит данные по [position]
     */
    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(data[position])

    /**
     * Получить inflated [View] из переопределённого [getLayout]
     */
    private fun getView(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(parent.context).inflate(getLayout(viewType), parent, false)

    /**
     * Установка новых данных [newData] с удалением старых.
     */
    open fun setData(newData: List<Data>) {
        data.clear()
        data.addAll(newData)

        notifyDataSetChanged()
    }

    /**
     * Добавление новых данных [newData] без удаления старых.
     */
    open fun addData(vararg newData: Data) {
        data.addAll(newData)
        notifyItemRangeInserted(data.size - newData.size, newData.size)
    }

    /**
     * Добавление новых данных [newData] в начало списка.
     */
    open fun addDataAsFirst(vararg newData: Data) {
        data.addAll(0, newData.asList())

        notifyItemRangeInserted(0, newData.size)
    }
}

abstract class BaseVH<Data>(v: View) : RecyclerView.ViewHolder(v) {

    open fun bind(data: Data) = Unit

    fun getContext(): Context = itemView.context

    fun getString(id: Int) = itemView.resources.getString(id)

    fun getDrawable(id: Int) = ContextCompat.getDrawable(itemView.context, id)
}
