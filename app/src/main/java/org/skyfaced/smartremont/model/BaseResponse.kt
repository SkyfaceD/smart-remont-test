package org.skyfaced.smartremont.model

import kotlinx.serialization.Serializable
import org.skyfaced.smartremont.model.dto.ErrorDto

@Serializable
data class BaseResponse<out T : Any?>(
    val data: T?,
    val response: Boolean,
    val error: ErrorDto
)