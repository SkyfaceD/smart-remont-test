package org.skyfaced.smartremont.ui.shop.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.back
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.model.dto.ShopDetailsDto
import org.skyfaced.smartremont.ui.common.BaseState

class DetailsViewModel(
    private val modo: Modo,
    private val detailsRepository: DetailsRepository,
    private val shopId: Int,
    private val cityId: Int
) : ViewModel() {
    private val _detailsState = MutableStateFlow<BaseState<ShopDetailsDto>>(BaseState.OnLoading)
    val detailsState = _detailsState.asStateFlow()

    init {
        onFetchDetails()
    }

    fun onFetchDetails() = viewModelScope.launch { fetchDetails() }

    private suspend fun fetchDetails() {
        detailsRepository.getDetails(shopId, cityId)
            .catch { _detailsState.emit(BaseState.OnFailure(it.cause)) }
            .onStart { _detailsState.emit(BaseState.OnLoading) }
            .collect {
                if (it.data != null && it.response) {
                    _detailsState.emit(BaseState.OnSuccess(it.data))
                } else {
                    _detailsState.emit(BaseState.OnFailure(message = it.error.message))
                }
            }
    }

    /**
     * Navigation
     */
    fun navigateBack() = modo.back()
}