package org.skyfaced.smartremont.ui.shop.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.model.adapter.CityItem
import org.skyfaced.smartremont.model.adapter.ShopItem
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.model.dto.ShopDto
import org.skyfaced.smartremont.navigation.Screens
import org.skyfaced.smartremont.ui.common.BaseState

class ShopsViewModel(
    private val modo: Modo,
    private val shopsRepository: ShopsRepository
) : ViewModel() {
    private val _citiesState = MutableSharedFlow<BaseState<List<CityItem>>>()
    val citiesState = _citiesState.asSharedFlow()

    private var _cityItems = listOf<CityItem>()
    val cityItems get() = _cityItems

    private val _shopsState = MutableSharedFlow<BaseState<List<ShopItem>>>()
    val shopsState = _shopsState.asSharedFlow()

    private var _shopItems = listOf<ShopItem>()
    val shopItems get() = _shopItems

    init {
        onFetchCities()
    }

    fun onFetchCities() = viewModelScope.launch { fetchCities() }

    fun onFetchShops(position: Int) = viewModelScope.launch { fetchShops(position) }

    private suspend fun fetchCities() {
        shopsRepository.getCities()
            .catch { _citiesState.emit(BaseState.OnFailure(it.cause)) }
            .onStart { _citiesState.emit(BaseState.OnLoading) }
            .collect {
                if (it.data != null && it.response) {
                    val items = it.data.map(CityDto::toCityItem)
                    _cityItems = items
                    _citiesState.emit(BaseState.OnSuccess(items))
                } else {
                    _citiesState.emit(BaseState.OnFailure(message = it.error.message))
                }
            }
    }

    private suspend fun fetchShops(position: Int) {
        val cityId = _cityItems[position].id
        shopsRepository.getShops(cityId)
            .catch { _shopsState.emit(BaseState.OnFailure(it.cause)) }
            .onStart { _shopsState.emit(BaseState.OnLoading) }
            .collect {
                if (it.data != null && it.response) {
                    val items = it.data.map(ShopDto::toShopItem)
                    _shopItems = items
                    _shopsState.emit(BaseState.OnSuccess(items))
                } else {
                    _shopsState.emit(BaseState.OnFailure(message = it.error.message))
                }
            }
    }

    /**
     * Navigation
     */
    fun navigateToDetails(shopId: Int) = modo.forward(Screens.DetailsScreen(shopId))
}