package org.skyfaced.smartremont.ui.shop.details.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemGrafikBinding
import org.skyfaced.smartremont.model.adapter.GrafikItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder

class GrafikAdapter : RecyclerView.Adapter<GrafikAdapter.ViewHolder>() {
    var currentList = listOf<GrafikItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<GrafikItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        binding: ItemGrafikBinding
    ) : BaseViewHolder<ItemGrafikBinding, GrafikItem>(binding) {
        override fun onBind(item: GrafikItem) {
            super.onBind(item)
            binding {
                txtDay.text = item.day
                txtTime.text = item.time
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemGrafikBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size
}