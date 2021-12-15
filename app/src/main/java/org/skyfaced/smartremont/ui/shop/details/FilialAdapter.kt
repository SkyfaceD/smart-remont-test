package org.skyfaced.smartremont.ui.shop.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemFilialBinding
import org.skyfaced.smartremont.model.adapter.FilialItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener

class FilialAdapter(
    private val onItemClick: (FilialItem) -> Unit,
    private val onItemLongClick: (FilialItem) -> Boolean
) : RecyclerView.Adapter<FilialAdapter.ViewHolder>() {
    var currentList = listOf<FilialItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<FilialItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        binding: ItemFilialBinding
    ) : BaseViewHolder<ItemFilialBinding, FilialItem>(binding) {
        init {
            binding.root.setOnDebounceClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnDebounceClickListener
                onItemClick(item)
            }

            binding.root.setOnLongClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnLongClickListener false
                onItemLongClick(item)
            }
        }

        override fun onBind(item: FilialItem) {
            super.onBind(item)
            binding {
                txtAddress.text = item.address
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFilialBinding.inflate(
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