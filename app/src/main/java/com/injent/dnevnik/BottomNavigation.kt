package com.injent.dnevnik

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreens.Home,
        BottomBarScreens.Students
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        containerColor = Color.Transparent,
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreens,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBarItem(
        label = {
            Text(text = stringResource(id = screen.resTitle), color = MaterialTheme.colorScheme.onSurface)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = .5f),
            selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
            unselectedTextColor = Color.Transparent
        ),
        onClick = {
            if (navController.currentDestination!!.route != screen.route) {
                navController.navigate(screen.route)
            }
        },
        alwaysShowLabel = false
    )
}

class BottomNavActions(private val navController: NavController) {

    fun navigateToStudents() {
        navController.navigate(BottomBarScreens.Students.route)
    }

    fun navigateToHome() {
        navController.navigate(BottomBarScreens.Home.route)
    }
}

sealed class BottomBarScreens(
    val route: String,
    @StringRes val resTitle: Int,
    val icon: ImageVector
) {
    object Home : BottomBarScreens(
        route = "home",
        resTitle = R.string.home,
        icon = Icons.Default.Home
    )

    object Students : BottomBarScreens(
        route = "students",
        resTitle = R.string.students,
        icon = Icons.Rounded.Person
    )
}
