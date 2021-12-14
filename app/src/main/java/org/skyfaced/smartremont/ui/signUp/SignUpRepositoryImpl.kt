package org.skyfaced.smartremont.ui.signUp

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.body.PasswordBody
import org.skyfaced.smartremont.model.body.RegisterBody
import org.skyfaced.smartremont.model.body.SMSCodeVerifyBody
import org.skyfaced.smartremont.model.dto.EmptyDto
import org.skyfaced.smartremont.model.dto.RegisterDto
import org.skyfaced.smartremont.model.dto.SMSCodeDto
import org.skyfaced.smartremont.model.dto.TokenDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi

class SignUpRepositoryImpl(
    private val smartRemontApi: SmartRemontApi
) : SignUpRepository {
    override fun registerNumber(phoneNumber: String) = flow<BaseResponse<RegisterDto?>> {
        val body = RegisterBody(phoneNumber)
        emit(smartRemontApi.register(body))
    }

    override fun getSMSCode(id: Int) = flow<BaseResponse<SMSCodeDto?>> {
        emit(smartRemontApi.getCode(id))
    }

    override fun verify(id: Int, smsCode: String) = flow<BaseResponse<TokenDto?>> {
        val body = SMSCodeVerifyBody(id, smsCode)
        emit(smartRemontApi.smsCodeVerify(body))
    }

    override fun setPassword(password: String) = flow<BaseResponse<EmptyDto?>> {
        val body = PasswordBody(password)
        emit(smartRemontApi.password(body))
    }
}