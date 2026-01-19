package com.smtm.pickle.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smtm.pickle.presentation.navigation.navigator.VerdictNavigator
import com.smtm.pickle.presentation.navigation.route.VerdictDetailRoute
import com.smtm.pickle.presentation.navigation.route.VerdictGraphRoute
import com.smtm.pickle.presentation.navigation.route.VerdictRoute
import com.smtm.pickle.presentation.verdict.VerdictScreen
import com.smtm.pickle.presentation.verdict.detail.VerdictDetailScreen

fun NavGraphBuilder.verdictNavGraph(
    navController: NavController,
) {
    navigation<VerdictGraphRoute>(startDestination = VerdictRoute) {
        composable<VerdictRoute> {
            val navigator = rememberVerdictNavigator(navController)
            VerdictScreen(navigator = navigator)
        }

        composable<VerdictDetailRoute> {
            val navigator = rememberVerdictNavigator(navController)
            VerdictDetailScreen(navigator = navigator)
        }
    }
}

@Composable
private fun rememberVerdictNavigator(
    navController: NavController,
): VerdictNavigator = remember(navController) {
    object : VerdictNavigator {
        override fun navigateToDetail() {
            navController.navigate(VerdictDetailRoute)
        }

        override fun navigateBack() {
            navController.popBackStack()
        }
    }
}