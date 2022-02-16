package com.example.catfacts.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.catfacts.ui.theme.CatFactsAppTheme

@Composable
fun LoadingIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(LoadingIndicatorDimens.spacerWeight))
        Row {
            Spacer(modifier = Modifier.weight(LoadingIndicatorDimens.spacerWeight))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.weight(LoadingIndicatorDimens.spacerWeight))
        }
        Spacer(modifier = Modifier.weight(LoadingIndicatorDimens.spacerWeight))
    }
}

private object LoadingIndicatorDimens {
    const val spacerWeight = 1f
}

@Preview
@Composable
fun LoadingIndicatorPreview() {
    CatFactsAppTheme {
        Surface {
            LoadingIndicator()
        }
    }
}
