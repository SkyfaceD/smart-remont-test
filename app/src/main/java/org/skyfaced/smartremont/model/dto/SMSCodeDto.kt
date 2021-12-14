package org.skyfaced.smartremont.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SMSCodeDto(
    @SerialName("sms_code")
    val smsCode: String
)