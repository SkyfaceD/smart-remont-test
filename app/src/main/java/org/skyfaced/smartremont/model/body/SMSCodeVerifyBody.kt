package org.skyfaced.smartremont.model.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SMSCodeVerifyBody(
    val id: Int,
    @SerialName("sms_code")
    val smsCode: String
)