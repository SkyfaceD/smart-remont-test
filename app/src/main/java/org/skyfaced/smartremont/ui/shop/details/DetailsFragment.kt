package org.skyfaced.smartremont.ui.shop.details

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.color.MaterialColors
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentDetailsBinding
import org.skyfaced.smartremont.model.adapter.TagItem
import org.skyfaced.smartremont.model.dto.ShopDetailsDto
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.ui.common.BaseState
import org.skyfaced.smartremont.ui.shop.details.util.FilialAdapter
import org.skyfaced.smartremont.ui.shop.details.util.TagAdapter
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.lazySafetyNone
import org.skyfaced.smartremont.util.extensions.showSnack
import org.skyfaced.smartremont.util.ui.LetterDrawable
import org.skyfaced.smartremont.util.ui.SquareItemDecoration
import kotlin.random.Random.Default.nextInt

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private val args by lazySafetyNone { requireArguments() }
    private val viewModel by viewModel<DetailsViewModel> {
        parametersOf(args.getInt(ARG_SHOP_ID), args.getInt(ARG_CITY_ID))
    }

    private val filialAdapter by lazySafetyNone {
        FilialAdapter(::onFilialShareClick, ::onFilialContactClick, ::onFilialMapClick)
    }
    private val tagAdapter by lazySafetyNone { TagAdapter(::onTagItemClick) }
    private val maxHeight by lazySafetyNone {
        (binding.toolbar.appBar.height - binding.toolbar.innerToolbar.height).toFloat()
    }
    private val colorSurface by lazySafetyNone {
        MaterialColors.getColor(binding.toolbar.innerToolbar, R.attr.colorSurface)
    }

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentDetailsBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        with(content) {
            recyclerViewTags.adapter = tagAdapter
            recyclerViewFilials.apply {
                val offset = resources.getDimensionPixelOffset(R.dimen.offset_8dp)
                addItemDecoration(SquareItemDecoration(offset))
                adapter = filialAdapter
            }

        }

        with(toolbar) {
            innerToolbar.setNavigationOnClickListener {
                viewModel.navigateBack()
            }

            appBar.addOnOffsetChangedListener(appbarOffsetListener)
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.detailsState) { state ->
            when (state) {
                is BaseState.OnFailure -> onFailure()
                is BaseState.OnLoading -> onLoading()
                is BaseState.OnSuccess -> onSuccess(state.data)
            }
        }
    }

    private fun onSuccess(dto: ShopDetailsDto) = binding {
        loader.root.isVisible = false
        content.root.isVisible = true

        val filialItems = dto.shopFilials.map(ShopDetailsDto.ShopFilial::toFilialItem)
        filialAdapter.submitList(filialItems)

        val tagItems = dto.shopTags.map(ShopDetailsDto.ShopTags::toTagItem)
        tagAdapter.submitList(tagItems)

        val backgroundColor =
            Color.parseColor(LetterDrawable.colors[nextInt(LetterDrawable.colors.size)])
        content.imgLogo.load(dto.iconUrl ?: "") {
            val drawable = LetterDrawable(dto.shopName, 72f, backgroundColor)
            placeholder(drawable)
            error(drawable)
        }
        content.txtName.text = dto.shopName
        content.txtDescription.text = dto.shopDescription

        toolbar.imgCollapseLogo.load(dto.iconUrl ?: "") {
            val drawable = LetterDrawable(dto.shopName, 36f, backgroundColor)
            placeholder(drawable)
            error(drawable)
        }
        toolbar.txtCollapseName.text = dto.shopName
    }

    private fun onLoading() = binding {
        loader.root.isVisible = true
    }

    private fun onFailure() = binding {
        loader.root.isVisible = false
    }

    private fun onFilialShareClick(siteUrl: String) {
        if (siteUrl.isEmpty()) binding.showSnack(R.string.error_something_went_wrong)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "${binding.content.txtName.text} - $siteUrl"
            )
            putExtra(Intent.EXTRA_TITLE, "Preview")
            type = "text/plain"
        }

        startActivity(Intent.createChooser(sendIntent, "Share"))
    }

    private fun onFilialContactClick(phoneNumber: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phoneNumber")
        }

        startActivity(intent)
    }

    private fun onFilialMapClick(pair: Pair<Double, Double>) {
        binding.showSnack(R.string.lbl_under_development)
    }

    private fun onTagItemClick(item: TagItem) = binding {
        logcat { item.toString() }
        showSnack(R.string.lbl_under_development)
    }

    private val appbarOffsetListener = AppBarLayout.OnOffsetChangedListener { _, offset ->
        val percent = 1 - (-offset / maxHeight)

        binding.content.apply {
            imgLogo.alpha = percent
            txtName.alpha = percent
            txtDescription.alpha = percent
        }

        binding.toolbar.apply {
            imgCollapseLogo.alpha = 1 - percent
            txtCollapseName.alpha = 1 - percent
            val alpha = 255 - (255 * percent).toInt()
            innerToolbar.setBackgroundColor(
                ColorUtils.setAlphaComponent(
                    colorSurface,
                    if (alpha < 0) 0 else if (alpha > 255) 255 else alpha
                )
            )
        }
    }

    companion object {

        const val SCREEN_KEY = "detailsFragmentScreen"
        private const val ARG_SHOP_ID = "argShopId"
        private const val ARG_CITY_ID = "argCityId"
        fun create(shopId: Int, cityId: Int) = DetailsFragment().apply {
            arguments = bundleOf(
                ARG_SHOP_ID to shopId,
                ARG_CITY_ID to cityId
            )
        }
    }
}