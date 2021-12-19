package org.skyfaced.smartremont.ui.shop.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import logcat.asLog
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentShopsBinding
import org.skyfaced.smartremont.model.adapter.CityItem
import org.skyfaced.smartremont.model.adapter.ShopItem
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.ui.common.BaseState
import org.skyfaced.smartremont.util.SaveListArrayAdapter
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.lazySafetyNone
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.extensions.showSnack
import org.skyfaced.smartremont.util.ui.VerticalDivider

class ShopsFragment : BaseFragment<FragmentShopsBinding>() {
    private val viewModel by viewModel<ShopsViewModel>()

    private val shopsAdapter by lazySafetyNone { ShopsAdapter(::onItemClick) }
    private val citiesAdapter by lazySafetyNone {
        SaveListArrayAdapter(
            requireContext(),
            R.layout.item_list_popup_window,
            mutableListOf<String>()
        )
    }
    private val citiesPopupWindow by lazySafetyNone {
        ListPopupWindow(requireContext(), null, R.attr.listPopupWindowStyle).apply {
            anchorView = binding.btnMore
            isModal = true
            width = 500 // FIXME Replace with static dimension
        }
    }

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentShopsBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        swipeRefreshLayout.isEnabled = false

        recyclerView.apply {
            val height = resources.getDimensionPixelOffset(R.dimen.offset_1dp)
            val color = ContextCompat.getColor(context, R.color.divider)
            val startOffset = resources.getDimensionPixelOffset(R.dimen.offset_48dp)
            addItemDecoration(VerticalDivider(height, color, startOffset = startOffset))
            adapter = shopsAdapter
        }

        citiesPopupWindow.apply {
            setAdapter(citiesAdapter)
            setOnItemClickListener { _, _, position, _ ->
                viewModel.onFetchShops(position)
                citiesPopupWindow.dismiss()
            }
        }

        btnLogout.setOnDebounceClickListener {
            viewModel.logout {
                viewModel.replaceWithStart()
            }
        }

        btnMore.setOnDebounceClickListener {
            if (btnChooseCity.isEnabled && !citiesAdapter.isEmpty) {
                citiesPopupWindow.show()
            }
        }

        btnChooseCity.setOnDebounceClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.lbl_city_list))
                .setItems(citiesAdapter.items.toTypedArray()) { _, which ->
                    viewModel.onFetchShops(which)
                }
                .show()
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.citiesState) { state ->
            logcat { state.toString() }
            when (state) {
                is BaseState.OnFailure -> onCitiesFailure(state.cause, state.message)
                is BaseState.OnLoading -> onCitiesLoading()
                is BaseState.OnSuccess -> onCitiesSuccess(state.data)
            }
        }

        flowObserver(viewModel.shopsState) { state ->
            logcat { state.toString() }
            when (state) {
                is BaseState.OnFailure -> onShopsFailure(state.cause, state.message)
                is BaseState.OnLoading -> onShopsLoading()
                is BaseState.OnSuccess -> onShopsSuccess(state.data)
            }
        }
    }

    private fun onShopsSuccess(items: List<ShopItem>) = binding {
        btnChooseCity.isVisible = false
        swipeRefreshLayout.isVisible = true
        swipeRefreshLayout.isRefreshing = false
        val items =
            items.map { it.copy(shopCount = getString(R.string.lbl_shop_count, it.shopCount)) }
        shopsAdapter.submitList(items)
        lblNoShop.isVisible = items.isEmpty()
    }

    private fun onShopsLoading() = binding {
        btnChooseCity.isVisible = false
        lblNoShop.isVisible = false
        swipeRefreshLayout.isVisible = true
        swipeRefreshLayout.isRefreshing = true
    }

    private fun onShopsFailure(cause: Throwable?, message: String?) = binding {
        btnChooseCity.isVisible = true
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.isVisible = false
        showSnack(message ?: getString(R.string.error_something_went_wrong))
        logcat { cause?.asLog() ?: message ?: "Что-то пошло не так" }
    }

    private fun onCitiesSuccess(items: List<CityItem>) = binding {
        progressBar.isVisible = false
        btnChooseCity.isEnabled = true
        // Re-init anchor for prevent ui bug
        citiesPopupWindow.anchorView = btnMore
        citiesAdapter.clear()
        citiesAdapter.addAll(items.map { it.name })
    }

    private fun onCitiesLoading() = binding {
        progressBar.isVisible = true
    }

    private fun onCitiesFailure(cause: Throwable?, message: String?) = binding {
        progressBar.isVisible = false
        showSnack(message ?: getString(R.string.error_something_went_wrong))
        logcat { cause?.asLog() ?: message ?: "Что-то пошло не так" }
    }

    private fun onItemClick(item: ShopItem) {
        viewModel.navigateToDetails(item.id)
    }

    companion object {
        const val SCREEN_KEY = "shopsFragmentScreen"
    }
}