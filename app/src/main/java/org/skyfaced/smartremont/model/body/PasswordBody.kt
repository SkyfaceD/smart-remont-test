package org.skyfaced.smartremont.model.body

import kotlinx.serialization.Serializable

@Serializable
data class PasswordBody(
    val password: String
)