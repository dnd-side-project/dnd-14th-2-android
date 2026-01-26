package com.smtm.pickle.presentation.home.model

import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class LedgerTypeUi(
    @StringRes val stringResId: Int,
) {
    data object Income : LedgerTypeUi(R.string.ledger_type_income)
    data object Expense : LedgerTypeUi(R.string.ledger_type_expense)
}