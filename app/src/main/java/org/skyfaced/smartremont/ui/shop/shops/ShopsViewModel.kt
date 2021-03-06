package org.skyfaced.smartremont.ui.shop.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import com.github.terrakok.modo.newStack
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
    private val _citiesState = MutableStateFlow<BaseState<List<CityItem>>>(BaseState.OnLoading)
    val citiesState = _citiesState.asStateFlow()

    private var _cityItems = listOf<CityItem>()
    private var selectedCityId = 0

    private val _shopsState = MutableStateFlow<BaseState<List<ShopItem>>>(BaseState.OnWaiting)
    val shopsState = _shopsState.asStateFlow()

    private var _shopItems = listOf<ShopItem>()

    init {
        onFetchCities()
    }

    fun onFetchCities() = viewModelScope.launch { fetchCities() }

    fun onFetchShops(position: Int) = viewModelScope.launch { fetchShops(position) }

    fun logout(block: Unit.() -> Unit) = viewModelScope.launch { shopsRepository.logout(block) }

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
        selectedCityId = _cityItems[position].id
        shopsRepository.getShops(selectedCityId)
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
    fun navigateToDetails(shopId: Int) = modo.forward(Screens.DetailsScreen(shopId, selectedCityId))

    fun replaceWithStart() = modo.newStack(Screens.StartScreen())
}