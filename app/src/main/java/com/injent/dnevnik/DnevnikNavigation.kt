package com.injent.dnevnik

import androidx.navigation.NavController
import com.injent.dnevnik.DnevnikDestinations.PROFILE_ROUTE
import com.injent.dnevnik.DnevnikDestinations.SIGN_IN_ROUTE
import com.injent.dnevnik.DnevnikDestinations.WORK_ROUTE
import com.injent.dnevnik.DnevnikNavigationArgs.USER_ID
import com.injent.dnevnik.MangroveScreens.PROFILE
import com.injent.dnevnik.MangroveScreens.SIGN_IN_SCREEN
import com.injent.dnevnik.MangroveScreens.WORK

private object MangroveScreens {
    const val SIGN_IN_SCREEN = "signIn"
    const val WORK = "work"
    const val PROFILE = "profile"
}

object DnevnikNavigationArgs {
    const val USER_ID = "userId"
}

object DnevnikDestinations {
    const val SIGN_IN_ROUTE = SIGN_IN_SCREEN
    const val WORK_ROUTE = WORK
    const val PROFILE_ROUTE = "$PROFILE/{$USER_ID}"
}

class NavActions(private val navController: NavController) {

    fun navigateToSignIn() {
        navController.navigate(SIGN_IN_ROUTE)
    }

    fun navigateToWorkScreen() {
        navController.navigate(WORK_ROUTE)
    }

    fun navigateToProfile(userId: Long) {
        navController.navigate("$PROFILE/$userId")
    }
}