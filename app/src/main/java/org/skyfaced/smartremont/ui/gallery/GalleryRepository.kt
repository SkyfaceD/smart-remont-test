package org.skyfaced.smartremont.ui.gallery

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    fun getCachedImages(): Flow<List<Uri>>

    fun getTempFile(): Uri

    fun saveToCache(uri: Uri): Uri
}