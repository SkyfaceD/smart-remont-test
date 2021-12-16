package org.skyfaced.smartremont.ui.shop.details.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemChipBinding
import org.skyfaced.smartremont.model.adapter.TagItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder

//TODO Concat with [ContactsAdapter]
class TagAdapter(private val onItemClick: (TagItem) -> Unit) :
    RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    var currentList = listOf<TagItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<TagItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        binding: ItemChipBinding
    ) : BaseViewHolder<ItemChipBinding, TagItem>(binding) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemClick(item)
            }
        }

        override fun onBind(item: TagItem) {
            super.onBind(item)
            binding {
                chip.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemChipBinding.inflate(
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