package com.injent.dnevnik

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.injent.dnevnik.DnevnikDestinations.PROFILE_ROUTE
import com.injent.dnevnik.DnevnikDestinations.SIGN_IN_ROUTE
import com.injent.dnevnik.DnevnikDestinations.WORK_ROUTE
import com.injent.dnevnik.DnevnikNavigationArgs.USER_ID
import com.injent.dnevnik.ui.profile.ProfileScreen
import com.injent.dnevnik.ui.signin.SignInScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun DnevnikNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = SIGN_IN_ROUTE,
    navActions: NavActions = remember(navController) {
        NavActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(SIGN_IN_ROUTE) {
            SignInScreen(navActions = navActions)
        }
        composable(WORK_ROUTE) {
            BottomBarNavGraph(scope = coroutineScope, navActions = navActions)
        }
        composable(
            PROFILE_ROUTE,
            arguments = listOf(navArgument(USER_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            ProfileScreen(
                navActions = navActions,
                userId = backStackEntry.arguments?.getLong(USER_ID)!!
            )
        }
    }
}