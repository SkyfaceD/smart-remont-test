package org.skyfaced.smartremont.ui.shop.details.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemChipBinding
import org.skyfaced.smartremont.model.adapter.ContactItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder

//TODO Concat with [TagAdapter]
class ContactsAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var currentList = listOf<ContactItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<ContactItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        binding: ItemChipBinding
    ) : BaseViewHolder<ItemChipBinding, ContactItem>(binding) {
        init {
            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                onItemClick(item.phoneNumber)
            }
        }

        override fun onBind(item: ContactItem) {
            super.onBind(item)
            binding {
                chip.text = item.phoneNumber
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