package com.smtm.pickle.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Nickname : Screen("nickname")
    object Home : Screen("home")
    object Mypage : Screen("mypage")
    object Setting : Screen("setting")
}
