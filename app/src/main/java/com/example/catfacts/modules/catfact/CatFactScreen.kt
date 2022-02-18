package com.example.catfacts.modules.catfact

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catfacts.R
import com.example.catfacts.data.LocalImageHandler
import com.example.catfacts.extensions.asImageBitmap
import com.example.catfacts.extensions.findActivity
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.ui.composables.ErrorRetryView
import com.example.catfacts.ui.composables.LoadingIndicator
import com.example.catfacts.ui.composables.SingleAction
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import com.example.catfacts.ui.navigation.TopBarAction
import com.example.catfacts.util.ShareUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.viewModel

@Composable
fun CatFactScreen(onTopBarActionClicked: MutableStateFlow<TopBarAction?>) {
    val vm by viewModel<CatFactViewModel>()
    val localImageHandler: LocalImageHandler = get()
    val shareUtil: ShareUtil = get()
    val context = LocalContext.current
    val state by vm.state.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    SingleAction {
        coroutineScope.launch {
            onTopBarActionClicked.collect { topBarAction ->
                when (topBarAction) {
                    is TopBarAction.Share -> {
                        shareImageAndText(localImageHandler, shareUtil, context, state)
                        onTopBarActionClicked.value = null // mark as consumed
                    }
                    is TopBarAction.SaveToGallery -> {
                        saveCatImage(localImageHandler, context, state)
                        onTopBarActionClicked.value = null // mark as consumed
                    }
                    null -> {
                        // nothing to do here
                    }
                }
            }
        }
        vm.load()
    }

    CatFactScreen(state) { vm.load() }
}

@Composable
fun CatFactScreen(
    state: State<CatFactData>?,
    loadDataAction: () -> Unit
) {
    if (state == null) {
        return
    }

    when {
        state.loadingState == LoadingState.LOADING -> {
            LoadingIndicator()
        }
        state.loadingState == LoadingState.ERROR || state.data == null -> {
            ErrorRetryView(
                errorText = stringResource(R.string.error_generic)
            ) {
                loadDataAction()
            }
        }
        state.loadingState == LoadingState.SUCCESS -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
                Image(
                    bitmap = state.data.imageData.asImageBitmap(),
                    contentDescription = stringResource(R.string.cat_fact_screen_cat_image_description),
                    modifier = Modifier.size(CatFactScreenDimens.imageSize)
                )
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
                Text(
                    text = state.data.text,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
                Button(onClick = loadDataAction) {
                    Text(text = stringResource(R.string.cat_fact_screen_next_fact_button_label))
                }
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
            }
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
        Toast.makeText(activity, R.string.error_share_cat_fact_generic, Toast.LENGTH_SHORT).show()
    }
}

private object CatFactScreenDimens {
    val imageSize = 300.dp
    const val spacerWeight = 1f
}
