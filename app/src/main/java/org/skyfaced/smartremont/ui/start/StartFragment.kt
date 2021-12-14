package org.skyfaced.smartremont.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.databinding.FragmentStartBinding
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener

class StartFragment : BaseFragment<FragmentStartBinding>() {
    private val viewModel by viewModel<StartViewModel>()

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentStartBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    private fun setupContent() = binding {
        containerSplash.isVisible = false
        containerStart.isVisible = true

        btnSignUp.setOnDebounceClickListener {
            viewModel.navigateToSignUpScreen()
        }

        btnSignIn.setOnDebounceClickListener {
            viewModel.navigateToSignInScreen()
        }

//        binding.root.postDelayed({ viewModel.navigateToMultiScreen() }, 500L)
    }

    companion object {
        const val SCREEN_KEY = "startFragmentScreen"
    }
}