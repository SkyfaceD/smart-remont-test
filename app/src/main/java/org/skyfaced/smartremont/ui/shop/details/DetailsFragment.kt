package org.skyfaced.smartremont.ui.shop.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class DetailsFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val SCREEN_KEY = "detailsFragmentScreen"

        private const val ARG_SHOP_ID = "argShopId"
        fun create(shopId: Int) = DetailsFragment().apply {
            arguments = bundleOf(ARG_SHOP_ID to shopId)
        }
    }
}