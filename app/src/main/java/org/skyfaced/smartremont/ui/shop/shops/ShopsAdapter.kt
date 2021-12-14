package org.skyfaced.smartremont.ui.shop.shops

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.skyfaced.smartremont.databinding.ItemShopBinding
import org.skyfaced.smartremont.model.adapter.ShopItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.ui.LetterDrawable

class ShopsAdapter(val onItemClick: (ShopItem) -> Unit) :
    RecyclerView.Adapter<ShopsAdapter.ViewHolder>() {
    var currentList = emptyList<ShopItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<ShopItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemShopBinding) :
        BaseViewHolder<ItemShopBinding, ShopItem>(binding) {
        init {
            binding.root.setOnDebounceClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnDebounceClickListener
                onItemClick(item)
            }
        }

        override fun onBind(item: ShopItem) {
            super.onBind(item)
            binding {
                imgLogo.load(item.imageUrl) {
                    val drawable = LetterDrawable(item.name)
                    placeholder(drawable)
                    error(drawable)
                }
                txtTitle.text = item.name
                txtSubtitle.text = item.shopCount
                txtCashback.text = item.cashback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemShopBinding.inflate(
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