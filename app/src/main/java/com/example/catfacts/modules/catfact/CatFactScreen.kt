package com.example.catfacts.modules.catfact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.catfacts.R
import com.example.catfacts.extensions.asImageBitmap
import com.example.catfacts.modules.catfact.model.CatFactData
import com.example.catfacts.modules.catfact.util.TopBarActionHandler
import com.example.catfacts.ui.composables.ErrorRetryView
import com.example.catfacts.ui.composables.LoadingIndicator
import com.example.catfacts.ui.composables.SingleAction
import com.example.catfacts.ui.model.LoadingState
import com.example.catfacts.ui.model.State
import com.example.catfacts.ui.navigation.TopBarAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.viewModel

@Composable
fun CatFactScreen(onTopBarActionClicked: MutableStateFlow<TopBarAction?>) {
    val vm by viewModel<CatFactViewModel>()
    val context = LocalContext.current
    val topBarActionHandler: TopBarActionHandler = get()
    val state by vm.state.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    SingleAction {
        coroutineScope.launch {
            onTopBarActionClicked.collect { topBarAction ->
                topBarAction?.let { action ->
                    topBarActionHandler.handleTopBarAction(
                        context = context,
                        topBarAction = action,
                        state = state
                    ) {
                        onTopBarActionClicked.value = null
                    }
                }
            }
        }
        vm.load()
    }

    CatFactScreen(
        state = state,
        loadDataAction = vm::load
    )
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
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    bitmap = state.data.imageData.asImageBitmap(),
                    contentDescription = stringResource(R.string.cat_fact_screen_cat_image_description),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(CatFactScreenDimens.imageWeight),
                    contentScale = ContentScale.FillWidth,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colors.secondary),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(CatFactScreenDimens.spacerHeight))
                    Text(
                        text = state.data.text,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(CatFactScreenDimens.spacerHeight))
                    Button(onClick = loadDataAction) {
                        Text(text = stringResource(R.string.cat_fact_screen_next_fact_button_label))
                    }
                    Spacer(modifier = Modifier.height(CatFactScreenDimens.spacerHeight))
                }
            }
        }
    }
}

private object CatFactScreenDimens {
    const val imageWeight = 1f
    val spacerHeight = 10.dp
}
