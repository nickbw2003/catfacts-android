package com.example.catfacts.modules.catfact

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catfacts.R
import com.example.catfacts.extensions.asImageBitmap
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.ui.composables.ErrorRetryView
import com.example.catfacts.ui.composables.LoadingIndicator
import com.example.catfacts.ui.composables.SingleAction
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import org.koin.androidx.compose.viewModel

@Composable
fun CatFactScreen() {
    val vm by viewModel<CatFactViewModel>()
    val state by vm.state.collectAsState(initial = null)
    SingleAction { vm.load() }
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
                errorText = stringResource(R.string.generic_error)
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
                    contentDescription = stringResource(R.string.cat_image),
                    modifier = Modifier.size(CatFactScreenDimens.imageSize)
                )
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
                Text(
                    text = state.data.text,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
                Button(onClick = loadDataAction) {
                    Text(text = stringResource(R.string.next_fact))
                }
                Spacer(modifier = Modifier.weight(CatFactScreenDimens.spacerWeight))
            }
        }
    }
}

private object CatFactScreenDimens {
    val imageSize = 300.dp
    const val spacerWeight = 1f
}
