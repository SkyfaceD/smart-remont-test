package org.skyfaced.smartremont.ui.shop.details

import kotlinx.coroutines.flow.Flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.ShopDetailsDto

interface DetailsRepository {
    fun getDetails(shopId: Int, cityId: Int): Flow<BaseResponse<ShopDetailsDto?>>
}
