package com.smtm.pickle.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smtm.pickle.presentation.navigation.route.HomeGraphRoute
import com.smtm.pickle.presentation.navigation.route.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
) {
    navigation<HomeGraphRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            // HomeScreen()
        }
    }
}