package com.example.catfacts.ui.composables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.catfacts.R
import com.example.catfacts.ui.navigation.Screen

@Composable
fun NavigationBar(
    screens: List<Screen>,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = screens.firstOrNull { screen ->
        screen.route == currentDestination?.route
    }
    val currentAppBarLabel = currentScreen?.labelResourceId ?: R.string.app_name

    TopAppBar(
        title = { Text(stringResource(currentAppBarLabel)) },
        actions = {
            currentScreen?.topBarActions?.map { action ->
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = stringResource(action.labelResourceId)
                    )
                }
            }
        }
    )
}
