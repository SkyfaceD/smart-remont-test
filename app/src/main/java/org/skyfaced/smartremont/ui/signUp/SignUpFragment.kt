package org.skyfaced.smartremont.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import org.skyfaced.smartremont.R
import org.skyfaced.smartremont.databinding.FragmentSignUpBinding
import org.skyfaced.smartremont.ui.common.BaseFragment
import org.skyfaced.smartremont.util.extensions.setOnDebounceClickListener

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentSignUpBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    private fun setupContent() = binding {
        notification.root.setOnDebounceClickListener {
            val smsCode = notification.txtNotificationSubtitle.text
                ?.toString()
                ?.takeLast(4)
                .orEmpty()
            edtSmsCode.setText(smsCode)
        }

        btnNext.setOnDebounceClickListener {
            if (tilNumber.isVisible) {
                tilNumber.isInvisible = true
                tilSmsCode.isVisible = true
                showNotification("1234")
            } else {
                tilSmsCode.isVisible = false
                btnNext.isVisible = false
                grpPassword.isVisible = true
            }
        }

        edtSmsCode.doOnTextChanged { _, _, _, _ ->
            notification.root.isVisible = false
        }
    }

    private fun showNotification(smsCode: String) = binding {
        notification.txtNotificationSubtitle.text =
            getString(R.string.lbl_notification_subtitle, smsCode)
        notification.root.isVisible = true
    }

    companion object {
        const val SCREEN_KEY = "signUpFragmentScreen"
    }
}