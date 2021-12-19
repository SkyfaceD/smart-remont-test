package org.skyfaced.smartremont.ui.shop.shops

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.model.dto.ShopDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi
import org.skyfaced.smartremont.util.ApplicationPreferences

class ShopsRepositoryImpl(
    private val productionSmartRemontApi: SmartRemontApi,
    private val mockSmartRemontApi: SmartRemontApi,
    private val preferences: ApplicationPreferences
) : ShopsRepository {
    override fun getCities() = flow<BaseResponse<List<CityDto>?>> {
        emit(productionSmartRemontApi.cityList())
    }

    override fun getShops(cityId: Int) = flow<BaseResponse<List<ShopDto>?>> {
        emit(productionSmartRemontApi.shopList(cityId))
    }

    override suspend fun logout(block: Unit.() -> Unit) {
        mockSmartRemontApi.clientLogout()
        preferences.clearTokens(block)
    }
}