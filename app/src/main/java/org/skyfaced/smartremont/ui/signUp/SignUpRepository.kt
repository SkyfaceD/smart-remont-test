package org.skyfaced.smartremont.ui.signUp

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.EmptyDto
import org.skyfaced.smartremont.model.dto.RegisterDto
import org.skyfaced.smartremont.model.dto.SMSCodeDto
import org.skyfaced.smartremont.model.dto.TokenDto

interface SignUpRepository {
    fun registerNumber(phoneNumber: String): Flow<BaseResponse<RegisterDto?>>

    fun getSMSCode(id: Int): Flow<BaseResponse<SMSCodeDto?>>

    fun verify(id: Int, smsCode: String): Flow<BaseResponse<TokenDto?>>

    fun setPassword(password: String): Flow<BaseResponse<EmptyDto?>>
}
