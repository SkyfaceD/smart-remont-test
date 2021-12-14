package org.skyfaced.smartremont.ui.shop.cities

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto

interface CitiesRepository {
    fun getCities(): Flow<BaseResponse<List<CityDto>?>>
}