package org.skyfaced.smartremont.ui.gallery

import android.net.Uri
import kotlinx.coroutines.flow.flow
import org.skyfaced.smartremont.util.FileHelper

class GalleryRepositoryImpl(private val fileHelper: FileHelper) : GalleryRepository {
    override fun getCachedImages() = flow<List<Uri>> {
        emit(fileHelper.getAllPhotosFromCacheDir())
    }

    override fun getTempFile(): Uri = fileHelper.getTempFileUri()

    override fun saveToCache(uri: Uri): Uri = fileHelper.saveToTemp(uri)
}