package org.skyfaced.smartremont.model.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    @SerialName("phone_number")
    val phoneNumber: String
)