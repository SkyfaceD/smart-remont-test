package org.skyfaced.smartremont.ui.shop.shops

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.model.dto.ShopDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi

class ShopsRepositoryImpl(private val smartRemontApi: SmartRemontApi) : ShopsRepository {
    override fun getCities() = flow<BaseResponse<List<CityDto>?>> {
        emit(smartRemontApi.cityList())
    }

    override fun getShops(cityId: Int) = flow<BaseResponse<List<ShopDto>?>> {
        emit(smartRemontApi.shopList(cityId))
    }
}