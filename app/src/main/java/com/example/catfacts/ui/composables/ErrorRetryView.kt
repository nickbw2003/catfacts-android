package com.example.catfacts.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.catfacts.R
import com.example.catfacts.ui.theme.CatFactsAppTheme

@Composable
fun ErrorRetryView(
    errorText: String,
    retryAction: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(ErrorRetryViewDimens.spacerWeight))
        Text(
            text = errorText,
            textAlign = TextAlign.Center
        )
        Button(onClick = retryAction) {
            Text(text = stringResource(R.string.common_action_retry))
        }
        Spacer(modifier = Modifier.weight(ErrorRetryViewDimens.spacerWeight))
    }
}

private object ErrorRetryViewDimens {
    const val spacerWeight = 1f
}

@Preview
@Composable
fun ErrorRetryViewPreview() {
    CatFactsAppTheme {
        Surface {
            ErrorRetryView(errorText = "An error occurred. Please try again.") {}
        }
    }
}
