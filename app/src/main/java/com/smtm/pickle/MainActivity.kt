package com.smtm.pickle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.GlobalNavEvent
import com.smtm.pickle.presentation.navigation.PickleNavHost
import com.smtm.pickle.presentation.navigation.route.LoginRoute
import com.smtm.pickle.presentation.navigation.route.MainGraphRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PickleTheme {
                val navController = rememberNavController()
                val handleGlobalNavEvent: (GlobalNavEvent) -> Unit = remember(navController) {
                    { event ->
                        when (event) {
                            is GlobalNavEvent.Logout -> {
                                navController.navigate(LoginRoute) {
                                    popUpTo(MainGraphRoute) { inclusive = true }
                                }
                            }

                            is GlobalNavEvent.SessionExpired -> {
                                navController.navigate(LoginRoute) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    }
                }

                PickleNavHost(
                    navController = navController,
                    onGlobalNavEvent = handleGlobalNavEvent,
                )
            }
        }
    }
}
