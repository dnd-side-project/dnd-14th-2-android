package com.smtm.pickle.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
data object LedgerCreateRoute

@Serializable
data class LedgerDetailRoute(
    val ledgerId: Long
)

@Serializable
data class LedgerEditRoute(
    val ledgerId: Long
)