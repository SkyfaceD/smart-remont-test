package org.skyfaced.smartremont.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val code: Int,
    val message: String?
)