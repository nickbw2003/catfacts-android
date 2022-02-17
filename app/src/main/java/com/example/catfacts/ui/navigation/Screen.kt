package com.example.catfacts.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.catfacts.R

sealed class Screen(
    val route: String,
    @StringRes val labelResourceId: Int,
    val icon: ImageVector,
    val topBarActions: List<TopBarAction> = emptyList()
) {

    object CatFact : Screen(
        route = "catfact",
        labelResourceId = R.string.cat_fact,
        icon = Icons.Filled.Face,
        topBarActions = listOf(
            TopBarAction.Share,
            TopBarAction.SaveToGallery
        )
    )

    object CatList : Screen(
        route = "catlist",
        labelResourceId = R.string.cat_list,
        icon = Icons.Filled.List
    )
}
