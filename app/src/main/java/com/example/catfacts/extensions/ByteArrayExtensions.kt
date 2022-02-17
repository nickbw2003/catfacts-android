package com.example.catfacts.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun ByteArray.asImageBitmap(): ImageBitmap {
    return asBitmap().asImageBitmap()
}

fun ByteArray.asBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
