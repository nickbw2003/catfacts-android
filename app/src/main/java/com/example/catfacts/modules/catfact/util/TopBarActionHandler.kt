package com.example.catfacts.modules.catfact.util

import android.content.Context
import android.widget.Toast
import com.example.catfacts.R
import com.example.catfacts.data.LocalImageHandler
import com.example.catfacts.extensions.findActivity
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.ui.model.State
import com.example.catfacts.ui.navigation.TopBarAction
import com.example.catfacts.util.ShareUtil

interface TopBarActionHandler {
    fun handleTopBarAction(
        context: Context,
        topBarAction: TopBarAction,
        state: State<CatFactData>?,
        onHandled: () -> Unit
    )
}

class TopBarActionHandlerImpl(
    private val localImageHandler: LocalImageHandler,
    private val shareUtil: ShareUtil
) : TopBarActionHandler {

    override fun handleTopBarAction(
        context: Context,
        topBarAction: TopBarAction,
        state: State<CatFactData>?,
        onHandled: () -> Unit
    ) {
        when (topBarAction) {
            is TopBarAction.Share -> {
                shareImageAndText(localImageHandler, shareUtil, context, state)
                onHandled()
            }
            is TopBarAction.SaveToGallery -> {
                saveCatImage(localImageHandler, context, state)
                onHandled()
            }
        }
    }

    private fun saveCatImage(
        localImageHandler: LocalImageHandler,
        context: Context,
        state: State<CatFactData>?
    ) {
        val activity = context.findActivity() ?: return
        val success = state?.data?.let { data ->
            localImageHandler.saveImageToGallery(data.imageData)
            true
        } ?: false

        val toastTextResource = if (success) {
            R.string.success_save_cat_image
        } else {
            R.string.error_save_cat_image_generic
        }
        Toast.makeText(activity, toastTextResource, Toast.LENGTH_SHORT).show()
    }

    private fun shareImageAndText(
        localImageHandler: LocalImageHandler,
        shareUtil: ShareUtil,
        context: Context,
        state: State<CatFactData>?
    ) {
        val activity = context.findActivity() ?: return
        val success = state?.data?.let { data ->
            localImageHandler.saveImageToGallery(
                data.imageData
            )?.let { imageUri ->
                val text = data.text
                shareUtil.shareTextAndImage(activity, text, imageUri)
                true
            }
        } ?: false

        if (!success) {
            Toast.makeText(activity, R.string.error_share_cat_fact_generic, Toast.LENGTH_SHORT)
                .show()
        }
    }
}
