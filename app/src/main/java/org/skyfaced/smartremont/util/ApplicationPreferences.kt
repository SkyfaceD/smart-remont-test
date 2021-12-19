package org.skyfaced.smartremont.util

import android.content.Context
import androidx.core.content.edit

class ApplicationPreferences(context: Context) {
    private companion object {
        const val APPLICATION_PREFERENCES = "appPreferences"

        private const val ACCESS_TOKEN = "accessToken"
        private const val REFRESH_TOKEN = "refreshToken"
    }

    private val preferences =
        context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)

    val accessToken get() = preferences.getString(ACCESS_TOKEN, "").orEmpty()

    val refreshToken get() = preferences.getString(REFRESH_TOKEN, "").orEmpty()

    fun updateTokens(accessToken: String, refreshToken: String) = preferences.edit(commit = true) {
        putString(ACCESS_TOKEN, accessToken)
        putString(REFRESH_TOKEN, refreshToken)
    }

    fun clearTokens(block: Unit.() -> Unit) = preferences.edit(commit = true) {
        remove(ACCESS_TOKEN)
        remove(REFRESH_TOKEN)
    }.apply(block)
}