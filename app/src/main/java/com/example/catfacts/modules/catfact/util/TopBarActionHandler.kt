package com.example.catfacts.modules.catfact.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.catfacts.R
import com.example.catfacts.data.LocalImageHandler
import com.example.catfacts.extensions.findActivity
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.ui.model.State
import com.example.catfacts.ui.navigation.TopBarAction
import com.example.catfacts.util.ShareUtil

interface TopBarActionHandler {

    val onPermissionResult: (Boolean) -> Unit

    fun handleTopBarAction(
        activityContext: Context,
        topBarAction: TopBarAction,
        state: State<CatFactData>?,
        askForPermission: (String) -> Unit,
        onHandled: () -> Unit
    )
}

class TopBarActionHandlerImpl(
    private val applicationContext: Context,
    private val localImageHandler: LocalImageHandler,
    private val shareUtil: ShareUtil
) : TopBarActionHandler {

    override val onPermissionResult: (Boolean) -> Unit = { isGranted ->
        val toastTextResource = if (isGranted) {
            R.string.success_permission_granted
        } else {
            R.string.error_permission_required
        }
        Toast.makeText(applicationContext, toastTextResource, Toast.LENGTH_SHORT).show()
    }

    override fun handleTopBarAction(
        activityContext: Context,
        topBarAction: TopBarAction,
        state: State<CatFactData>?,
        askForPermission: (String) -> Unit,
        onHandled: () -> Unit
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val hasStoragePermission = ContextCompat.checkSelfPermission(
                activityContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasStoragePermission) {
                askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                onHandled()
                return
            }
        }

        when (topBarAction) {
            is TopBarAction.Share -> {
                shareImageAndText(localImageHandler, shareUtil, activityContext, state)
                onHandled()
            }
            is TopBarAction.SaveToGallery -> {
                saveCatImage(localImageHandler, activityContext, state)
                onHandled()
            }
        }
    }

    private fun saveCatImage(
        localImageHandler: LocalImageHandler,
        activityContext: Context,
        state: State<CatFactData>?
    ) {
        val activity = activityContext.findActivity() ?: return
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
        activityContext: Context,
        state: State<CatFactData>?
    ) {
        val activity = activityContext.findActivity() ?: return
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
            Toast.makeText(
                activity,
                R.string.error_share_cat_fact_generic,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
