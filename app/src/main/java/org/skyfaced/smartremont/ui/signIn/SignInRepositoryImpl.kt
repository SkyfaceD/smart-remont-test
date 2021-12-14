package org.skyfaced.smartremont.ui.signIn

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.body.ClientLoginBody
import org.skyfaced.smartremont.model.dto.TokenDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi
import org.skyfaced.smartremont.util.ApplicationPreferences

class SignInRepositoryImpl(
    private val smartRemontApi: SmartRemontApi,
    private val preferences: ApplicationPreferences
) : SignInRepository {
    override fun signIn(number: String, password: String) = flow<BaseResponse<TokenDto?>> {
        val body = ClientLoginBody(number, password)
        emit(smartRemontApi.clientLogin(body))
    }

    override fun saveTokens(accessToken: String, refreshToken: String) {
        preferences.updateTokens(accessToken, refreshToken)
    }
}