package org.skyfaced.smartremont.util

import android.content.Context
import androidx.core.content.edit

class ApplicationPreferences(context: Context) {
    private companion object {
        const val APPLICATION_PREFERENCES = "appPreferences"

        private const val LOGIN_TOKEN = "loginToken"
        private const val REFRESH_TOKEN = "refreshToken"
    }

    private val preferences =
        context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)

    val loginToken get() = preferences.getString(LOGIN_TOKEN, "").orEmpty()

    val refreshToken get() = preferences.getString(REFRESH_TOKEN, "").orEmpty()

    fun updateTokens(loginToken: String, refreshToken: String) = preferences.edit(commit = true) {
        putString(LOGIN_TOKEN, loginToken)
        putString(REFRESH_TOKEN, refreshToken)
    }
}