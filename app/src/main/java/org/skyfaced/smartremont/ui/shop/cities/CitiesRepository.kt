package org.skyfaced.smartremont.ui.shop.cities

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto

@Deprecated("")
interface CitiesRepository {
    fun getCities(): Flow<BaseResponse<List<CityDto>?>>
}