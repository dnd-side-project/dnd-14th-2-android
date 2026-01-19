package com.smtm.pickle.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.route.MainGraphRoute

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    onGlobalNavEvent: (GlobalNavEvent) -> Unit,
) {
    navigation<MainGraphRoute>(startDestination = MainGraphRoute) {
        homeNavGraph(navController)
        verdictNavGraph(navController)
        myPageNavGraph(navController, onGlobalNavEvent)
    }
}