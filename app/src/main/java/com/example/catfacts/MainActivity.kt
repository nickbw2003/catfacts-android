package com.example.catfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catfacts.modules.catfact.CatFactScreen
import com.example.catfacts.modules.catlist.CatListScreen
import com.example.catfacts.ui.composables.BottomNavigationBar
import com.example.catfacts.ui.composables.NavigationBar
import com.example.catfacts.ui.navigation.Screen
import com.example.catfacts.ui.navigation.TopBarAction
import com.example.catfacts.ui.theme.CatFactsAppTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatFactsAppTheme {
                Main()
            }
        }
    }
}

@Composable
fun Main() {
    val screens = listOf(
        Screen.CatFact,
        Screen.CatList,
    )
    val navController = rememberNavController()
    val onTopBarActionClicked = MutableStateFlow<TopBarAction?>(null)

    Scaffold(
        topBar = {
            NavigationBar(
                screens = screens,
                navController = navController,
            ) { topBarAction ->
                onTopBarActionClicked.value = topBarAction
            }
        },
        bottomBar = {
            BottomNavigationBar(
                screens = screens,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.CatFact.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.CatFact.route) { CatFactScreen(onTopBarActionClicked) }
            composable(Screen.CatList.route) { CatListScreen() }
        }
    }
}
