package org.skyfaced.smartremont.ui.gallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.skyfaced.smartremont.databinding.ItemPhotoBinding
import org.skyfaced.smartremont.model.adapter.PhotoItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    var currentList = listOf<PhotoItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(items: List<PhotoItem>) {
        currentList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        binding: ItemPhotoBinding
    ) : BaseViewHolder<ItemPhotoBinding, PhotoItem>(binding) {
        override fun onBind(item: PhotoItem) {
            super.onBind(item)
            binding {
                imgPhoto.setImageURI(item.uri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPhotoBinding.inflate(
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