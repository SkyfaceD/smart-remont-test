package org.skyfaced.smartremont.ui.shop.shops

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import org.skyfaced.smartremont.databinding.FragmentShopsBinding
import org.skyfaced.smartremont.ui.common.BaseFragment

class ShopsFragment : BaseFragment<FragmentShopsBinding>() {
    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentShopsBinding.inflate(inflater, parent, false)

    companion object {
        const val SCREEN_KEY = "shopsFragmentScreen"

        private const val ARG_CITY_ID = "argCityId"
        fun create(cityId: Int) = ShopsFragment().apply {
            arguments = bundleOf(ARG_CITY_ID to cityId)
        }
    }
}