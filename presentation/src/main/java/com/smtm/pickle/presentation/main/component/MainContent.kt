package com.smtm.pickle.presentation.main.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.home.HomeScreen
import com.smtm.pickle.presentation.mypage.MyPageScreen
import com.smtm.pickle.presentation.navigation.PickleBottomNavigationBar
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.HomeTabRoute
import com.smtm.pickle.presentation.navigation.route.JurorDetailRoute
import com.smtm.pickle.presentation.navigation.route.JurorListRoute
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.MyLedgerRoute
import com.smtm.pickle.presentation.navigation.route.MyPageTabRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute
import com.smtm.pickle.presentation.navigation.route.VerdictCreateRoute
import com.smtm.pickle.presentation.navigation.route.VerdictRequestRoute
import com.smtm.pickle.presentation.navigation.route.VerdictResultRoute
import com.smtm.pickle.presentation.navigation.route.VerdictTabRoute
import com.smtm.pickle.presentation.verdict.VerdictScreen

@Composable
fun MainContent(
    rootNavController: NavHostController,
    tabNavController: NavHostController,
    currentDestination: NavDestination?,
    onBottomBarHeightChange: (Int) -> Unit,
) {
    Scaffold(
        bottomBar = {
            PickleBottomNavigationBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    onBottomBarHeightChange(coordinates.size.height)
                },
                currentDestination = currentDestination,
                onNavigate = { route ->
                    tabNavController.navigate(route) {
                        popUpTo(tabNavController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = tabNavController,
            startDestination = HomeTabRoute,
            builder = {
                composable<HomeTabRoute> {
                    HomeScreen(
                        onNavigateToLedgerCreate = {
                            rootNavController.navigate(LedgerCreateRoute)
                        },
                        onNavigateToLedgerDetail = { id ->
                            rootNavController.navigate(LedgerDetailRoute(id))
                        }
                    )
                }

                composable<VerdictTabRoute> {
                    VerdictScreen(
                        onNavigateVerdictCreate = {
                            rootNavController.navigate(VerdictCreateRoute)
                        },
                        onNavigateVerdictRequest = {
                            rootNavController.navigate(VerdictRequestRoute)
                        },
                        onNavigateVerdictResult = {
                            rootNavController.navigate(VerdictResultRoute)
                        },
                        onNavigateJurorList = {
                            rootNavController.navigate(JurorListRoute)
                        },
                        onNavigateJurorDetail = {
                            rootNavController.navigate(JurorDetailRoute)
                        }
                    )
                }

                composable<MyPageTabRoute> {
                    MyPageScreen(
                        onNavigateMyLedger = {
                            rootNavController.navigate(MyLedgerRoute)
                        },
                        onNavigateSetting = {
                            rootNavController.navigate(SettingRoute)
                        },
                        onNavigateAlarmSetting = {
                            rootNavController.navigate(AlarmSettingRoute)
                        }
                    )
                }
            }
        )
    }
}