package org.skyfaced.smartremont.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import logcat.LogPriority
import logcat.asLog
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentSignUpBinding
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.util.consts.Validation
import org.skyfaced.smartremont.util.extensions.flowObserver
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener
import org.skyfaced.smartremont.util.extensions.showSnack

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val viewModel by viewModel<SignUpViewModel>()

    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentSignUpBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
        setupObserver()
    }

    private fun setupContent() = binding {
        val shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        notification.root.setOnDebounceClickListener {
            val smsCode = notification.txtNotificationSubtitle.text
                ?.toString()
                ?.takeLast(4)
                .orEmpty()
            edtSmsCode.setText(smsCode)
            notification.root.isVisible = false
            btnNext.callOnClick()
        }

        btnNext.setOnDebounceClickListener {
            if (tilNumber.isVisible) {
                val phoneNumber = edtNumber.text?.toString().orEmpty()

                if (phoneNumber.isEmpty()) {
                    tilNumber.error = getString(R.string.error_fill_empty_field)
                    tilNumber.startAnimation(shakeAnimation)
                    return@setOnDebounceClickListener
                }

                if (phoneNumber.length < Validation.MIN_NUMBER_LENGTH) {
                    tilNumber.error =
                        getString(R.string.error_number_length, Validation.MIN_NUMBER_LENGTH)
                    tilNumber.startAnimation(shakeAnimation)
                    return@setOnDebounceClickListener
                }

                viewModel.registerNumber(phoneNumber)
            } else {
                if (notification.root.isVisible) notification.root.isVisible = false

                val smsCode = edtSmsCode.text?.toString().orEmpty()

                if (smsCode.isEmpty()) {
                    tilSmsCode.error = getString(R.string.error_fill_empty_field)
                    tilSmsCode.startAnimation(shakeAnimation)
                    return@setOnDebounceClickListener
                }

                if (smsCode.length < Validation.MIN_SMS_CODE_LENGTH) {
                    tilSmsCode.error =
                        getString(R.string.error_number_length, Validation.MIN_SMS_CODE_LENGTH)
                    tilSmsCode.startAnimation(shakeAnimation)
                    return@setOnDebounceClickListener
                }

                viewModel.verify(smsCode)
            }
        }

        btnSignUp.setOnDebounceClickListener {
            val password = edtPassword.text?.toString().orEmpty()
            val confirmPassword = edtConfirmPassword.text?.toString().orEmpty()
            val fields = listOf(password to tilPassword, confirmPassword to tilConfirmPassword)

            if (fields.map { showErrorOnNullOrEmptyField(it) }.any { !it }) {
                if (password.length < Validation.MIN_PASSWORD_LENGTH) tilPassword.error =
                    getString(R.string.error_password_length, Validation.MIN_PASSWORD_LENGTH)
            }

            val list = listOf(tilPassword, tilConfirmPassword)
            if (list.any { it.isErrorEnabled }) {
                list.filter { it.isErrorEnabled }.forEach { it.startAnimation(shakeAnimation) }
                return@setOnDebounceClickListener
            }

            if (password != confirmPassword) {
                tilConfirmPassword.error = getString(R.string.error_confirm_password)
                tilConfirmPassword.startAnimation(shakeAnimation)
                return@setOnDebounceClickListener
            }

            viewModel.setPassword(password)
        }

        listOf(
            edtNumber to tilNumber,
            edtSmsCode to tilSmsCode,
            edtPassword to tilPassword,
            edtConfirmPassword to tilConfirmPassword
        ).forEach { pair ->
            pair.first.doAfterTextChanged {
                if (!pair.second.isErrorEnabled) return@doAfterTextChanged
                pair.second.isErrorEnabled = false
            }
        }
    }

    private fun setupObserver() {
        flowObserver(viewModel.signUpState) { state ->
            when (state) {
                is SignUpState.OnFailure -> onFailure(state.cause, state.message)
                is SignUpState.OnLoading -> onLoading(state.state)
                is SignUpState.OnSuccess<*> -> onSuccess(state.state, state.data)
            }
        }
    }

    private fun onSuccess(state: SignUp, data: Any?) = binding {
        progressBar.isVisible = false

        when (state) {
            SignUp.Register -> {
                val id = data as Int
                tilNumber.isInvisible = true
                tilSmsCode.isVisible = true
                btnNext.isEnabled = true
                viewModel.getSMSCode(id)
            }
            SignUp.SMSCode -> {
                val smsCode = data as String
                showNotification(smsCode)
            }
            SignUp.Verify -> {
                tilNumber.isVisible = false
                tilSmsCode.isVisible = false
                btnNext.isVisible = false
                grpPassword.isVisible = true
            }
            SignUp.Password -> {
                showSnack(R.string.msg_registration_success, length = Snackbar.LENGTH_LONG)
                viewModel.navigateBackToRoot()
            }
        }
    }

    private fun onLoading(state: SignUp) = binding {
        when (state) {
            SignUp.Register,
            SignUp.Verify -> {
                progressBar.isVisible = true
                btnNext.isEnabled = false
            }
            SignUp.Password -> {
                progressBar.isVisible = true
                btnSignUp.isEnabled = false
            }
        }
    }

    private fun onFailure(cause: Throwable?, message: String?) = binding {
        progressBar.isVisible = false
        btnNext.isEnabled = true
        btnSignUp.isEnabled = true
        showSnack(message ?: cause?.message ?: getString(R.string.error_something_went_wrong))
        logcat(priority = LogPriority.ERROR) {
            cause?.asLog() ?: message ?: getString(R.string.error_something_went_wrong)
        }
    }

    private fun showErrorOnNullOrEmptyField(pair: Pair<String?, TextInputLayout>): Boolean {
        when (pair.first.isNullOrEmpty()) {
            true -> pair.second.error = getString(R.string.error_fill_empty_field)
            false -> pair.second.isErrorEnabled = false
        }

        return pair.second.isErrorEnabled
    }

    private fun showNotification(smsCode: String) = binding {
        notification.txtNotificationSubtitle.text =
            getString(R.string.lbl_notification_subtitle, smsCode)
        notification.root.isVisible = true
        notification.root.postDelayed({ notification.root.isVisible = false }, 5_000L)
    }

    companion object {
        const val SCREEN_KEY = "signUpFragmentScreen"
    }
}