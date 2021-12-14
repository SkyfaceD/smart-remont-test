package org.skyfaced.smartremont.model.info

data class SignUpInfo(
    val id: Int? = null,
    val smsCode: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)