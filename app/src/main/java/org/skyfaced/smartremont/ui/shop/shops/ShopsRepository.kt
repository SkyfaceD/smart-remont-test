package org.skyfaced.smartremont.ui.shop.shops

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.model.dto.ShopDto

interface ShopsRepository {
    fun getCities(): Flow<BaseResponse<List<CityDto>?>>

    fun getShops(cityId: Int): Flow<BaseResponse<List<ShopDto>?>>
}