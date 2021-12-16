package org.skyfaced.smartremont.ui.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import logcat.logcat
import org.skyfaced.smartremont.databinding.FragmentWebBinding
import org.skyfaced.smartremont.ui.common.BaseFragment

class WebFragment : BaseFragment<FragmentWebBinding>() {
    override fun setupBinding(inflater: LayoutInflater, parent: ViewGroup?) =
        FragmentWebBinding.inflate(inflater, parent, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupContent()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setupContent() = binding {
        webView.postDelayed({
            webView.webViewClient = ReactWebClient()
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(BASE_URL)
        }, 1_500L)
    }

    inner class ReactWebClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressBar.isVisible = false
            logcat { "Web page loaded" }
        }
    }

    companion object {
        const val SCREEN_KEY = "webFragmentScreen"

        private const val BASE_URL = "https://framework7.io/kitchen-sink/react/index.html?theme=md"
    }
}