package com.smtm.pickle.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.designsystem.components.picklenavhost.DimOverlay
import com.smtm.pickle.presentation.designsystem.components.picklenavhost.ExpandableFab
import com.smtm.pickle.presentation.designsystem.components.picklenavhost.PickleNavHostDefaults
import com.smtm.pickle.presentation.navigation.graph.authNavGraph
import com.smtm.pickle.presentation.navigation.graph.mainNavGraph
import com.smtm.pickle.presentation.navigation.route.AuthGraphRoute
import com.smtm.pickle.presentation.navigation.route.CreateLedgerRoute
import com.smtm.pickle.presentation.navigation.route.HomeRoute
import com.smtm.pickle.presentation.navigation.route.MainGraphRoute
import com.smtm.pickle.presentation.navigation.route.MyPageRoute
import com.smtm.pickle.presentation.navigation.route.VerdictRoute

@Composable
fun PickleNavHost(
    navController: NavHostController = rememberNavController(),
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val shouldShowBottomBar = currentDestination.shouldShowBottomBar()
    val isHomeScreen = currentDestination?.hasRoute(HomeRoute::class) == true

    var isFabExpanded by rememberSaveable { mutableStateOf(false) }
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        PickleScaffold(
            navController = navController,
            shouldShowBottomBar = shouldShowBottomBar,
            currentDestination = currentDestination,
            onGlobalNavEvent = onGlobalNavEvent,
            onBottomBarHeightChange = { height ->
                bottomBarHeight = with(density) { height.toDp() }
            },
        )

        if (isHomeScreen && isFabExpanded) {
            DimOverlay(onClick = { isFabExpanded = false })
        }

        if (isHomeScreen) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = bottomBarHeight + PickleNavHostDefaults.FabBottomPadding,
                        end = PickleNavHostDefaults.FabEndPadding
                    )
            ) {
                ExpandableFab(
                    expendedText = "가계부 작성하기",
                    expandedActionImageVector = Icons.Filled.Edit,
                    collapsedActionImageVector = Icons.Filled.Add,
                    isExpanded = isFabExpanded,
                    onExpandChanged = { isFabExpanded = it },
                    onActionClick = {
                        isFabExpanded = false
                        navController.navigate(CreateLedgerRoute)
                    }
                )
            }
        }
    }
}

private fun NavDestination?.shouldShowBottomBar(): Boolean {
    val bottomBarRoutes = setOf(
        HomeRoute::class,
        VerdictRoute::class,
        MyPageRoute::class,
    )
    return bottomBarRoutes.any { routeClass ->
        this?.hasRoute(routeClass) == true
    }
}

@Composable
private fun PickleScaffold(
    navController: NavHostController,
    shouldShowBottomBar: Boolean,
    currentDestination: NavDestination?,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
    onBottomBarHeightChange: (Int) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                PickleBottomNavigationBar(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        onBottomBarHeightChange(coordinates.size.height)
                    },
                    currentDestination = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(MainGraphRoute) { saveState = true }
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
            startDestination = MainGraphRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
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

    }
}