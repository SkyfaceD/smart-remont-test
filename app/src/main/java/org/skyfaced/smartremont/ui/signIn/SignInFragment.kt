package org.skyfaced.smartremont.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentSignInBinding
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.util.extensions.showSnack

class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentSignInBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    private fun setupContent() = binding {
        txtForgotPassword.setOnClickListener {
            showSnack(R.string.lbl_under_development)
        }
    }

    companion object {
        const val SCREEN_KEY = "signInFragmentScreen"
    }
}