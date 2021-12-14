package org.skyfaced.smartremont.model.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientLoginBody(
    @SerialName("phone_number")
    val phoneNumber: String,
    val password: String
)