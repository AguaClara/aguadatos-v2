package com.example.aguadatos_v2.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.aguadatos_v2.android.eatery.ui.screens.HomeScreen
import com.aguadatos_v2.android.eatery.ui.screens.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationSetup() {
    val navController = rememberAnimatedNavController()
    val showBottomBar = rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            AnimatedContent(
                targetState = showBottomBar.value,
                transitionSpec = {
                    expandVertically(
                        animationSpec = tween(durationMillis = 500),
                        expandFrom = Alignment.Bottom
                    ) togetherWith shrinkVertically(animationSpec = tween(durationMillis = 500))
                }
            ) { state ->
                if (state) {
                    BottomNavigationBar(navController, NavigationItem.bottomNavTabList)
                }
            }
        }
    ) { innerPadding ->
        SetupNavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            showBottomBar = showBottomBar
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, tabItems: List<NavigationItem>) {
    NavigationBar(
        containerColor = Color.White,
        // TODO: Replace with AguaDatosBlue
        contentColor = Color.Black
    ) {
        val currentRoute = currentRoute(navController)
        tabItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (item.selectedRoutes.contains(currentRoute)) item.selectedIconId else item.unselectedIconId
                        ),
                        contentDescription = item.route
                    )
                },
                selected = item.selectedRoutes.contains(currentRoute),
                selectedContentColor = Color.Unspecified,
                unselectedContentColor = Color.Unspecified,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    showBottomBar: MutableState<Boolean>
) {
    // TODO: Once OnboardScreen is implemented, a function is called to decide HomeScreen or OnboardScreen
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.HOME.route
    ) {
        composable(
            Routes.HOME.route,
            enterTransition = {
                fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(durationMillis = 500)
                )
            }) {
            HomeScreen()
        }
        composable(
            route = Routes.SETTINGS.route,
            enterTransition = {
                fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(durationMillis = 500)
                )
            }) {
            SettingsScreen()
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

fun NavController.isOnBackStack(route: String): Boolean = try {
    getBackStackEntry(route); true
} catch (e: Throwable) {
    false
}