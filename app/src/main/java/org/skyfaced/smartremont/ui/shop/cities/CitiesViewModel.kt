package org.skyfaced.smartremont.ui.shop.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.model.adapter.CityItem
import org.skyfaced.smartremont.model.dto.CityDto
import org.skyfaced.smartremont.navigation.Screens
import org.skyfaced.smartremont.ui.common.BaseState

class CitiesViewModel(
    private val modo: Modo,
    private val citiesRepository: CitiesRepository
) : ViewModel() {
    private val _citiesState = MutableSharedFlow<BaseState<List<CityItem>>>()
    val citiesState = _citiesState.asSharedFlow()

    init {
        viewModelScope.launch {
            fetchCities()
        }
    }

    fun onTryAgain() = viewModelScope.launch { fetchCities() }

    suspend fun fetchCities() {
        citiesRepository.getCities()
            .catch { _citiesState.emit(BaseState.OnFailure(it.cause)) }
            .onStart { _citiesState.emit(BaseState.OnLoading) }
            .collect {
                if (it.data != null && it.response) {
                    val items = it.data.map(CityDto::toCityItem)
                    _citiesState.emit(BaseState.OnSuccess(items))
                } else {
                    _citiesState.emit(BaseState.OnFailure(message = it.error.message))
                }
            }
    }

    /**
     * Navigation
     */
    fun navigateToShops(cityId: Int) = modo.forward(Screens.ShopsScreen(cityId))
}
