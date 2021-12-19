package org.skyfaced.smartremont.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import org.skyfaced.smartremont.BuildConfig
import java.io.File

class FileHelper(private val context: Context) {
    fun getAllPhotosFromCacheDir(): List<Uri> {
        val cacheDir = context.cacheDir
        if (!cacheDir.exists()) return emptyList()

        val images = cacheDir.listFiles()
            ?.filter { it.name.startsWith(IMAGE_FILE_PREFIX) }
            ?.map { it.toUri() }

        return images ?: emptyList()
    }

    fun getTempFileUri(): Uri {
        val tempFile = File.createTempFile(IMAGE_FILE_PREFIX, ".jpg", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            tempFile
        )
    }

    // Saves without any cropping
    fun saveToTemp(uri: Uri): Uri {
        val imageByteArray: ByteArray
        context.contentResolver.openInputStream(uri).use {
            imageByteArray = it?.readBytes() ?: ByteArray(0)
        }

        val extension = uri.toString().substringAfterLast('.')
        val tempFile =
            File.createTempFile(IMAGE_FILE_PREFIX, ".$extension", context.cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        val cache = FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            tempFile
        )

        context.contentResolver.openOutputStream(cache).use {
            it?.write(imageByteArray)
        }

        return cache
    }

    companion object {
        private const val IMAGE_FILE_PREFIX = "temp_image"
    }
}