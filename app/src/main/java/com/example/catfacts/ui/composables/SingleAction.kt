package com.example.catfacts.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SingleAction(action: () -> Unit) {
    LaunchedEffect(true) {
        action()
    }
}
