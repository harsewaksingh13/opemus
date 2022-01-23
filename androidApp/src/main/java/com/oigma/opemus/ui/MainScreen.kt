package com.oigma.opemus.ui


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Created by Harsewak Singh on 20/01/2022.
 */
@Composable
fun MainScreen(navController: NavHostController, content: @Composable () -> Unit) {

    val bottomNavigationItems = listOf(
        BottomNavigationScreen.Home,
        BottomNavigationScreen.Explore,
        BottomNavigationScreen.Library,
        BottomNavigationScreen.Upgrade
    )
    Scaffold(
        bottomBar = {
            AppBottomNavigation(navController, bottomNavigationItems)
        }, modifier = Modifier.padding(top = Dp(20f), bottom = Dp(48f))
    ) {
        content()
    }
}

sealed class BottomNavigationScreen(
    val route: String,
    val resourceId: String,
    val icon: ImageVector
) {
    object Home : BottomNavigationScreen("home", "Home", Icons.Filled.Terrain)
    object Explore : BottomNavigationScreen("explore", "Explore", Icons.Filled.FoodBank)
    object Library : BottomNavigationScreen("library", "Library", Icons.Filled.Fireplace)
    object Upgrade : BottomNavigationScreen("more", "More", Icons.Filled.Cake)
}


@Composable
fun SimpleScreen(
    text: String
) {
    Text(text = text)
}

@Composable
private fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreen>
) {
    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, "", modifier = Modifier.fillMaxWidth()) },
                label = { Text(screen.resourceId) },
                selected = currentRoute == screen.route,
                onClick = {
                    // This if check gives us a "singleTop" behavior where we do not create a
                    // second instance of the composable if we are already on that destination
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString("KEY_ROUTE")
}