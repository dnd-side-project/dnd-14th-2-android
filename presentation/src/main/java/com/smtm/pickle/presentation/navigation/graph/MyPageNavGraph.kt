package com.smtm.pickle.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.smtm.pickle.presentation.mypage.MyPageScreen
import com.smtm.pickle.presentation.mypage.alarmsetting.AlarmSettingScreen
import com.smtm.pickle.presentation.mypage.setting.SettingScreen
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.navigator.MyPageNavigator
import com.smtm.pickle.presentation.navigation.route.AlarmSettingRoute
import com.smtm.pickle.presentation.navigation.route.MyPageGraphRoute
import com.smtm.pickle.presentation.navigation.route.MyPageRoute
import com.smtm.pickle.presentation.navigation.route.SettingRoute

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    navigation<MyPageGraphRoute>(startDestination = MyPageRoute) {
        composable<MyPageRoute> {
            val navigator = rememberMyPageNavigator(navController, onGlobalNavEvent)
            MyPageScreen(navigator = navigator)
        }

        composable<SettingRoute> {
            val navigator = rememberMyPageNavigator(navController, onGlobalNavEvent)
            SettingScreen(navigator = navigator)
        }

        composable<AlarmSettingRoute> {
            val navigator = rememberMyPageNavigator(navController, onGlobalNavEvent)
            AlarmSettingScreen(navigator = navigator)
        }
    }
}

@Composable
private fun rememberMyPageNavigator(
    navController: NavController,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
): MyPageNavigator = remember(navController) {
    object : MyPageNavigator {
        override fun navigateToSetting() {
            navController.navigate(SettingRoute)
        }

        override fun navigateToAlarmSetting() {
            navController.navigate(AlarmSettingRoute)
        }

        override fun logout() {
            onGlobalNavEvent(GlobalNavEvent.Logout)
        }

        override fun navigateBack() {
            navController.popBackStack()
        }
    }
}