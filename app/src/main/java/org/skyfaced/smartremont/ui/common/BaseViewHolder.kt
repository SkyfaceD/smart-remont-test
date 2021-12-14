package org.skyfaced.smartremont.ui.common

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.skyfaced.smartremont.model.adapter.Item

abstract class BaseViewHolder<VB : ViewBinding, I : Item>(
    protected val binding: VB
) : RecyclerView.ViewHolder(binding.root) {
    private var _item: I? = null
    protected val item get() = requireNotNull(_item) { "Item isn't initialized" }

    protected fun binding(block: VB.() -> Unit) = binding.apply(block)

    protected val context: Context get() = binding.root.context

    open fun onBind(item: I) {
        _item = item
    }

    open fun onBind(item: I, payloads: MutableList<Any>) {
        _item = item
    }
}