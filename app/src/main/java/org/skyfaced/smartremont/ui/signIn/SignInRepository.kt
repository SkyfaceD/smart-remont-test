package org.skyfaced.smartremont.ui.signIn

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.TokenDto

interface SignInRepository {
    fun signIn(number: String, password: String): Flow<BaseResponse<TokenDto?>>

    fun saveTokens(accessToken: String, refreshToken: String)
}