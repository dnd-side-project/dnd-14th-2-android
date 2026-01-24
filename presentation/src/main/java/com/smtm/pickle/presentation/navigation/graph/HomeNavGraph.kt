package com.smtm.pickle.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smtm.pickle.presentation.createledger.CreateLedgerScreen
import com.smtm.pickle.presentation.createledger.CreateLedgerStep
import com.smtm.pickle.presentation.home.HomeScreen
import com.smtm.pickle.presentation.navigation.navigator.HomeNavigator
import com.smtm.pickle.presentation.navigation.route.CreateLedgerRoute
import com.smtm.pickle.presentation.navigation.route.HomeGraphRoute
import com.smtm.pickle.presentation.navigation.route.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    navigation<HomeGraphRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            val navigator = rememberHomeNavigator(navController)
            HomeScreen(navigator = navigator)
        }

        composable<CreateLedgerRoute> {
            CreateLedgerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun rememberHomeNavigator(
    navController: NavController
): HomeNavigator = remember(navController) {
    object : HomeNavigator {
        override fun navigateBack() {
            navController.navigateUp()
        }
    }
}