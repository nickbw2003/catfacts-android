package com.example.catfacts.data.api

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.example.catfacts.extensions.asBitmap

interface LocalImageHandler {
    fun saveImageToGallery(
        imageName: String,
        imageData: ByteArray
    ): Uri?
}

class LocalImageHandlerImpl(
    private val appContext: Context
) : LocalImageHandler {

    private val contentResolver: ContentResolver by lazy {
        appContext.contentResolver
    }

    override fun saveImageToGallery(
        imageName: String,
        imageData: ByteArray
    ): Uri? {
        val mediaStoreUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val bitmap = imageData.asBitmap()
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$imageName.$fileType")
            put(MediaStore.Images.Media.MIME_TYPE, mimeType)
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }
        return try {
            val uri = contentResolver.insert(mediaStoreUri, contentValues) ?: return null
            contentResolver.openOutputStream(uri).use { outputStream ->
                val saved = bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, outputStream)
                if (saved) {
                    uri
                } else {
                    null
                }
            }
        } catch (t: Throwable) {
            return null
        }
    }

    companion object {
        private const val fileType = "jpg"
        private const val mimeType = "image/jpeg"
        private const val imageQuality = 95
    }
}
