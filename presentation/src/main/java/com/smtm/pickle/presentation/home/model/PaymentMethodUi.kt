package com.smtm.pickle.presentation.home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class PaymentMethodUi(
    @StringRes val stringResId: Int,
    @DrawableRes val activatedIconResId: Int,
    @DrawableRes val inactivatedIconResId: Int,
) {
    data object BankTransfer : PaymentMethodUi(
        stringResId = R.string.ledger_payment_method_bank_transfer,
        activatedIconResId = R.drawable.ic_ledger_payment_method_bank_transfer_activated,
        inactivatedIconResId = R.drawable.ic_ledger_payment_method_bank_transfer_inactivated,
    )

    data object CreditCard : PaymentMethodUi(
        stringResId = R.string.ledger_payment_method_credit_card,
        activatedIconResId = R.drawable.ic_ledger_payment_method_credit_card_activated,
        inactivatedIconResId = R.drawable.ic_ledger_payment_method_credit_card_inactivated,
    )

    data object DebitCard : PaymentMethodUi(
        stringResId = R.string.ledger_payment_method_debit_card,
        activatedIconResId = R.drawable.ic_ledger_payment_method_debit_card_activated,
        inactivatedIconResId = R.drawable.ic_ledger_payment_method_debit_card_inactivated,
    )

    data object Cash : PaymentMethodUi(
        stringResId = R.string.ledger_payment_method_cash,
        activatedIconResId = R.drawable.ic_ledger_payment_method_cash_activated,
        inactivatedIconResId = R.drawable.ic_ledger_payment_method_cash_inactivated,
    )
}