package org.skyfaced.smartremont.ui.shop.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import logcat.asLog
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentCitiesBinding
import org.skyfaced.smartremont.model.adapter.CityItem
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.ui.common.BaseState
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.lazySafetyNone
import org.skyfaced.smartremont.util.extensions.showSnack

class CitiesFragment : BaseFragment<FragmentCitiesBinding>() {
    private val viewModel by viewModel<CitiesViewModel>()

    private val citiesAdapter by lazySafetyNone { CitiesAdapter(::onItemClick) }

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentCitiesBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        swipeRefreshLayout.isEnabled = false

        recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = citiesAdapter
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.citiesState) { state ->
            when (state) {
                is BaseState.OnFailure -> onFailure(state.cause, state.message)
                is BaseState.OnLoading -> onLoading()
                is BaseState.OnSuccess -> onSuccess(state.data)
            }
        }
    }

    private fun onSuccess(items: List<CityItem>) = binding {
        swipeRefreshLayout.isRefreshing = false
        citiesAdapter.submitList(items)
    }

    private fun onLoading() = binding {
        swipeRefreshLayout.isRefreshing = true
    }

    private fun onFailure(cause: Throwable?, message: String?) = binding {
        swipeRefreshLayout.isRefreshing = false
        showSnack(message ?: getString(R.string.error_something_went_wrong))
        logcat { cause?.asLog() ?: message ?: "Что-то пошло не так" }
    }

    private fun onItemClick(item: CityItem) {
        logcat { "Selected item $item" }
        viewModel.navigateToShops(item.id)
    }

    companion object {
        const val SCREEN_KEY = "citiesFragmentScreen"
    }
}