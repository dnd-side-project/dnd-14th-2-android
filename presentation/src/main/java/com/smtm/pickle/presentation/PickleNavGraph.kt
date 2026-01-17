package com.smtm.pickle.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.home.HomeScreen
import com.smtm.pickle.presentation.login.LoginScreen
import com.smtm.pickle.presentation.mypage.MypageScreen
import com.smtm.pickle.presentation.navigation.Screen
import com.smtm.pickle.presentation.nickname.NicknameScreen
import com.smtm.pickle.presentation.onboarding.OnboardingScreen
import com.smtm.pickle.presentation.setting.SettingScreen
import com.smtm.pickle.presentation.splash.SplashScreen

@Composable
fun PickleNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen() { navController.navigate(Screen.Onboarding.route) }
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen() { navController.navigate(Screen.Login.route) }
        }

        composable(Screen.Login.route) {
            LoginScreen() { navController.navigate(Screen.Nickname.route) }
        }

        composable(Screen.Nickname.route) {
            NicknameScreen() { navController.navigate(Screen.Home.route) }
        }

        composable(Screen.Home.route) {
            HomeScreen() { navController.navigate(Screen.Mypage.route) }
        }

        composable(Screen.Mypage.route) {
            MypageScreen() { navController.navigate(Screen.Setting.route) }
        }

        composable(Screen.Setting.route) {
            SettingScreen()
        }
    }
}
