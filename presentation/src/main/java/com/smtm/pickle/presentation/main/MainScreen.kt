package com.smtm.pickle.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.main.component.DimOverlay
import com.smtm.pickle.presentation.main.component.HomeExpandableFab
import com.smtm.pickle.presentation.main.component.MainContent
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.route.HomeTabRoute

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isHomeScreen = currentDestination?.hasRoute(HomeTabRoute::class) == true

    var isFabExpanded by rememberSaveable { mutableStateOf(false) }
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        MainContent(
            rootNavController = rootNavController,
            tabNavController = tabNavController,
            currentDestination = currentDestination,
            onBottomBarHeightChange = { height ->
                bottomBarHeight = with(density) { height.toDp() }
            }
        )

        if (isHomeScreen && isFabExpanded) {
            DimOverlay(onClick = { isFabExpanded = false })
        }

        if (isHomeScreen) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = bottomBarHeight + 9.dp,
                        end = 16.dp
                    )
            ) {
                HomeExpandableFab(
                    isExpanded = isFabExpanded,
                    onOpenClick = { isFabExpanded = true },
                    onCloseClick = { isFabExpanded = false },
                    onCreateClick = { isFabExpanded = false },
                )
            }
        }
    }
}