package org.skyfaced.smartremont.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.skyfaced.smartremont.model.adapter.CityItem

@Serializable
data class CityDto(
    @SerialName("city_id")
    val cityId: Int,
    @SerialName("city_code")
    val cityCode: String,
    @SerialName("city_name")
    val cityName: String
) {
    fun toCityItem() = CityItem(
        id = cityId,
        name = cityName
    )
}