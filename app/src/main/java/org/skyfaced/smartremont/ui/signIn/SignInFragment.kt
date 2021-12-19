package org.skyfaced.smartremont.ui.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import logcat.LogPriority
import logcat.asLog
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentSignInBinding
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.ui.common.BaseState
import org.skyfaced.smartremont.util.consts.Validation
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.extensions.showSnack

class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    private val viewModel by viewModel<SignInViewModel>()

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentSignInBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        btnSignIn.setOnDebounceClickListener {
            val number = edtNumber.text?.toString().orEmpty()
            val password = edtPassword.text?.toString().orEmpty()
            val fields = listOf(number to tilNumber, password to tilPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.all { !it }) {
                if (number.length < Validation.MIN_NUMBER_LENGTH) tilNumber.error =
                    getString(R.string.error_number_length, Validation.MIN_NUMBER_LENGTH)

                if (password.length < Validation.MIN_PASSWORD_LENGTH) tilPassword.error =
                    getString(R.string.error_password_length, Validation.MIN_PASSWORD_LENGTH)
            }

            val tils = listOf(tilNumber, tilPassword)
            if (tils.any { it.isErrorEnabled }) {
                tils.filter { it.isErrorEnabled }.forEach { it.startAnimation(shakeAnimation) }
                return@setOnDebounceClickListener
            }

            viewModel.signIn(number, password)
        }

        listOf(
            edtNumber to tilNumber,
            edtPassword to tilPassword,
        ).forEach { pair ->
            pair.first.doAfterTextChanged {
                if (!pair.second.isErrorEnabled) return@doAfterTextChanged
                pair.second.isErrorEnabled = false
            }
        }

        txtForgotPassword.setOnClickListener {
            showSnack(R.string.lbl_under_development)
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.signInState) { state ->
            logcat { state.toString() }

            when (state) {
                is BaseState.OnFailure -> onFailure(state.cause, state.message)
                is BaseState.OnLoading -> onLoading()
                is BaseState.OnSuccess -> onSuccess()
            }
        }
    }

    private fun onFailure(cause: Throwable?, message: String?) = binding {
        progressBar.isVisible = false
        btnSignIn.isEnabled = true
        showSnack(message ?: cause?.message ?: getString(R.string.error_something_went_wrong))
        logcat(priority = LogPriority.ERROR) {
            cause?.asLog() ?: message ?: getString(R.string.error_something_went_wrong)
        }
    }

    private fun onLoading() = binding {
        progressBar.isVisible = true
        btnSignIn.isEnabled = false
    }

    private fun onSuccess() = binding {
        progressBar.isVisible = false
        viewModel.showMultiScreen()
    }

    private fun showErrorOnNullOrEmptyField(pair: Pair<String?, TextInputLayout>): Boolean {
        when (pair.first.isNullOrEmpty()) {
            true -> pair.second.error = getString(R.string.error_fill_empty_field)
            false -> pair.second.isErrorEnabled = false
        }

        return pair.second.isErrorEnabled
    }

    companion object {
        const val SCREEN_KEY = "signInFragmentScreen"
    }
}