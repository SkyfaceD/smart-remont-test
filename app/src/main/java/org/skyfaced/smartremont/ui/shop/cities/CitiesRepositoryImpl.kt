package org.skyfaced.smartremont.ui.shop.cities

import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.model.BaseResponse
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.network.smartRemont.SmartRemontApi

class CitiesRepositoryImpl(private val smartRemontApi: SmartRemontApi) : CitiesRepository {
    override fun getCities() = flow<BaseResponse<List<CityDto>?>> {
        emit(smartRemontApi.cityList())
    }
}