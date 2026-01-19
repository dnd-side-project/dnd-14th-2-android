package com.smtm.pickle.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.navigation.graph.authNavGraph
import com.smtm.pickle.presentation.navigation.graph.mainNavGraph
import com.smtm.pickle.presentation.navigation.route.AuthGraphRoute
import com.smtm.pickle.presentation.navigation.route.MainGraphRoute

@Composable
fun PickleNavHost(
    navController: NavHostController = rememberNavController(),
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bottom Navigation 표시 여부: 현재 destination이 Bottom Nav 탭의 Graph에 속하면 표시
    val shouldShowBottomBar = currentDestination?.hierarchy?.any { destination ->
        BottomNavItem.entries.any { item ->
            destination.route?.contains(item.graphRouteClass.simpleName ?: "") == true
        }
    } == true

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                PickleBottomNavigationBar(
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(MainGraphRoute) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AuthGraphRoute,
            modifier = Modifier.padding(innerPadding),
            builder = {
                authNavGraph(
                    navController = navController,
                    onNavigateToMain = {
                        navController.navigate(MainGraphRoute) {
                            popUpTo(AuthGraphRoute) { inclusive = true }
                        }
                    }
                )

                mainNavGraph(
                    navController = navController,
                    onGlobalNavEvent = onGlobalNavEvent
                )
            }
        )

    }
}