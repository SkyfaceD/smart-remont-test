package org.skyfaced.smartremont.ui.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.skyfaced.smartremont.model.adapter.PhotoItem
import org.skyfaced.smartremont.ui.common.BaseState

class GalleryViewModel(private val galleryRepository: GalleryRepository) : ViewModel() {
    private val _galleryState = MutableStateFlow<BaseState<List<PhotoItem>>>(BaseState.OnLoading)
    val galleryState = _galleryState.asStateFlow()

    val list = mutableListOf<PhotoItem>()

    val tempFileUri get() = galleryRepository.getTempFile()

    init {
        viewModelScope.launch { fetchGallery() }
    }

    fun saveToTemp(uri: Uri) = galleryRepository.saveToCache(uri)

    private suspend fun fetchGallery() {
        galleryRepository.getCachedImages()
            .catch { _galleryState.emit(BaseState.OnFailure(it)) }
            .onStart { _galleryState.emit(BaseState.OnLoading) }
            .collect { uris ->
                list.addAll(uris.map { PhotoItem(it) })
                _galleryState.emit(BaseState.OnSuccess(list))
            }
    }
}
