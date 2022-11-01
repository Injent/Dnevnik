package com.injent.dnevnik

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.injent.dnevnik.ui.home.HomeScreen
import com.injent.dnevnik.ui.topstudents.Students
import kotlinx.coroutines.CoroutineScope

@Composable
fun BottomBarNavGraph(
    navActions: NavActions,
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope
) {
    val navBar = @Composable { BottomNavigation(navController = navController) }
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Students.route
    ) {
        composable(route = BottomBarScreens.Students.route) {
            Students(
                navBar = navBar,
                scope = scope,
                navActions = navActions
            )
        }
        composable(route = BottomBarScreens.Home.route) {
            HomeScreen(
                navBar = navBar
            )
        }
    }
}