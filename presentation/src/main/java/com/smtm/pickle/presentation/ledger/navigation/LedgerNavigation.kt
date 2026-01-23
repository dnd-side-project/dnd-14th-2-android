package com.smtm.pickle.presentation.ledger.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.smtm.pickle.presentation.ledger.create.LedgerCreateScreen
import com.smtm.pickle.presentation.ledger.detail.LedgerDetailScreen
import com.smtm.pickle.presentation.ledger.edit.LedgerEditScreen
import com.smtm.pickle.presentation.navigation.route.LedgerCreateRoute
import com.smtm.pickle.presentation.navigation.route.LedgerDetailRoute
import com.smtm.pickle.presentation.navigation.route.LedgerEditRoute

fun NavGraphBuilder.ledgerDestinations(navController: NavController) {
    composable<LedgerCreateRoute> {
        LedgerCreateScreen()
    }

    composable<LedgerEditRoute> {
        LedgerEditScreen()
    }

    composable<LedgerDetailRoute> {
        LedgerDetailScreen()
    }
}