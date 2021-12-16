package org.skyfaced.smartremont.ui.shop.details.util

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.ItemFilialBinding
import org.skyfaced.smartremont.model.adapter.FilialItem
import org.skyfaced.smartremont.ui.common.BaseViewHolder
import org.skyfaced.smartremont.util.extensions.lazySafetyNone
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.ui.VerticalDivider

class FilialAdapter(
    private val onShareClick: (siteUrl: String) -> Unit,
    private val onContactClick: (phoneNumber: String) -> Unit,
    private val onMapClick: (coordinates: Pair<Double, Double>) -> Unit
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
        private val grafikAdapter by lazySafetyNone { GrafikAdapter() }
        private val contactsAdapter by lazySafetyNone { ContactsAdapter(onContactClick) }

        init {
            binding {
                btnShare.setOnDebounceClickListener {
                    if (adapterPosition == RecyclerView.NO_POSITION) return@setOnDebounceClickListener
                    onShareClick(item.siteUrl)
                }

                btnMap.setOnDebounceClickListener {
                    if (adapterPosition == RecyclerView.NO_POSITION) return@setOnDebounceClickListener
                    onMapClick(item.coordinates)
                }

                with(recyclerViewGrafik) {
                    setHasFixedSize(true)
                    val height = resources.getDimensionPixelOffset(R.dimen.offset_1dp)
                    val color = ContextCompat.getColor(context, R.color.divider)
                    addItemDecoration(VerticalDivider(height, color))
                    adapter = grafikAdapter
                }

                with(recyclerViewContacts) {
                    setHasFixedSize(true)
                    val lm = FlexboxLayoutManager(context)
                    lm.flexDirection = FlexDirection.ROW
                    lm.justifyContent = JustifyContent.FLEX_START
                    layoutManager = lm
                    adapter = contactsAdapter
                }
            }
        }

        override fun onBind(item: FilialItem) {
            super.onBind(item)
            binding {
                txtAddress.text = item.address
                grafikAdapter.submitList(item.grafik)
                contactsAdapter.submitList(item.contacts)
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