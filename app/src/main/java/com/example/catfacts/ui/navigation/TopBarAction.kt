package com.example.catfacts.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.catfacts.R

sealed class TopBarAction(
    @StringRes val labelResourceId: Int,
    val icon: ImageVector
) {
    object Share : TopBarAction(
        labelResourceId = R.string.top_bar_action_share,
        icon = Icons.Filled.Share
    )

    object SaveToGallery : TopBarAction(
        labelResourceId = R.string.top_bar_action_save_to_gallery,
        icon = Icons.Filled.Favorite
    )
}
