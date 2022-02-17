package com.example.catfacts.util

import android.content.Context
import android.content.Intent
import android.net.Uri

interface ShareUtil {
    fun shareTextAndImage(activityContext: Context, text: String, image: Uri)
}

class ShareUtilImpl : ShareUtil {

    override fun shareTextAndImage(activityContext: Context, text: String, image: Uri) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            putExtra(Intent.EXTRA_STREAM, image)
            type = "*/*"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        activityContext.startActivity(shareIntent)
    }
}
