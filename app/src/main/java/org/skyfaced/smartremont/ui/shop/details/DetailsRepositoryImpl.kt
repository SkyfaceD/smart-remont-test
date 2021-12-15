package org.skyfaced.smartremont.ui.shop.details

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.ShopDetailsDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi

class DetailsRepositoryImpl(private val smartRemontApi: SmartRemontApi) : DetailsRepository {
    override fun getDetails(shopId: Int, cityId: Int) = flow<BaseResponse<ShopDetailsDto?>> {
        try {
            smartRemontApi.getShop(shopId, cityId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emit(smartRemontApi.getShop(shopId, cityId))
    }
}