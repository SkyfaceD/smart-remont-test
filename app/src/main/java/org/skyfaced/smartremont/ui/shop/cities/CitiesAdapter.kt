package org.skyfaced.smartremont.ui.shop.cities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemCityBinding
import org.skyfaced.smartremont.model.adapter.CityItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener

class CitiesAdapter(val onItemClick: (CityItem) -> Unit) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    var currentList = emptyList<CityItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CityItem>) {
        currentList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemCityBinding) :
        BaseViewHolder<ItemCityBinding, CityItem>(binding) {
        init {
            binding.root.setOnDebounceClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnDebounceClickListener
                onItemClick(item)
            }
        }

        override fun onBind(item: CityItem) {
            super.onBind(item)
            binding {
                txtCity.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCityBinding.inflate(
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