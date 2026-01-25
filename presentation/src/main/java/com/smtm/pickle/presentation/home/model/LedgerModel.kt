package com.smtm.pickle.presentation.home.model

import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R
import java.time.LocalDate

data class LedgerModel(
    val id: Long,
    val type: LedgerTypeUi,
    val amount: String,
    val amountValue: Long,
    val category: CategoryUi,
    val description: String,
    val occurredOn: LocalDate,
    val dateText: String,
    val paymentMethod: PaymentMethodUi,
    val memo: String?
)

data class DailyLedgerModel(
    val date: LocalDate,
    val dateText: String,
    val ledgers: List<LedgerModel>,
    val totalIncome: String?,
    val totalExpense: String?,
)

sealed class LedgerTypeUi(
    @StringRes val stringResId: Int
) {
    data object Income : LedgerTypeUi(R.string.ledger_type_income)
    data object Expense : LedgerTypeUi(R.string.ledger_type_expense)
}

sealed class CategoryUi(
    @StringRes val stringResId: Int
) {
    data object Food : CategoryUi(R.string.ledger_category_food)
    data object Transport : CategoryUi(R.string.ledger_category_transport)
    data object Housing : CategoryUi(R.string.ledger_category_housing)
    data object Shopping : CategoryUi(R.string.ledger_category_shopping)
    data object HealthMedical : CategoryUi(R.string.ledger_category_health_medical)
    data object EducationSelfDevelopment : CategoryUi(R.string.ledger_category_education_self_development)
    data object LeisureHobby : CategoryUi(R.string.ledger_category_leisure_hobby)
    data object SavingFinance : CategoryUi(R.string.ledger_category_saving_finance)
    data object Other : CategoryUi(R.string.ledger_category_other)
}

sealed class PaymentMethodUi(
    @StringRes val stringResId: Int
) {
    data object BankTransfer : PaymentMethodUi(R.string.ledger_payment_method_bank_transfer)
    data object Card : PaymentMethodUi(R.string.ledger_payment_method_card)
    data object Cash : PaymentMethodUi(R.string.ledger_payment_method_cash)
    data object Other : PaymentMethodUi(R.string.ledger_payment_method_other)
}