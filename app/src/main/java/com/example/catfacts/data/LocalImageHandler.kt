package com.example.catfacts.data

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.example.catfacts.extensions.asBitmap
import java.text.SimpleDateFormat
import java.util.*

interface LocalImageHandler {
    fun saveImageToGallery(
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
        imageData: ByteArray
    ): Uri? {
        val mediaStoreUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val bitmap = imageData.asBitmap()
        val dateFormat = SimpleDateFormat.getDateTimeInstance().apply {
            (this as? SimpleDateFormat)?.applyPattern(dateTimePattern)
        }
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "${dateFormat.format(Date())}.$fileType")
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
        private const val dateTimePattern = "yyyyMMdd_HHmmssSS"
    }
}
