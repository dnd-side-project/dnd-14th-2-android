package com.smtm.pickle.presentation.mapper.ledger

import com.smtm.pickle.domain.model.ledger.Category
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.DailyLedgerModel
import com.smtm.pickle.presentation.home.model.LedgerModel
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import java.time.LocalDate

fun LedgerEntry.toUiModel(): LedgerModel = LedgerModel(
    id = id.value,
    type = type.toUiModel(),
    amount = amount.value.toMoneyFormat(),
    amountValue = amount.value,
    category = category.toUiModel(),
    description = description,
    occurredOn = occurredOn,
    dateText = occurredOn.toDisplayYMDDate(),
    paymentMethod = paymentMethod?.toUiModel() ?: PaymentMethodUi.Other,
    memo = memo
)

fun List<LedgerEntry>.toDailyLedgerUiModel(): List<DailyLedgerModel> =
    groupBy { it.occurredOn }
        .map { (date, entries) ->
            DailyLedgerModel(
                date = date,
                dateText = date.toDisplayMDDate(),
                ledgers = entries.map { it.toUiModel() },
                totalIncome = entries.filter { it.type == LedgerType.INCOME }
                    .sumOf { it.amount.value }
                    .takeIf { it > 0 }
                    ?.toMoneyFormat(),
                totalExpense = entries.filter { it.type == LedgerType.EXPENSE }
                    .sumOf { it.amount.value }
                    .takeIf { it > 0 }
                    ?.toMoneyFormat(),
            )
        }
        .sortedByDescending { it.date }

private fun LedgerType.toUiModel(): LedgerTypeUi = when (this) {
    LedgerType.INCOME -> LedgerTypeUi.Income
    LedgerType.EXPENSE -> LedgerTypeUi.Expense
}

private fun Category.toUiModel(): CategoryUi = when (this) {
    Category.FOOD -> CategoryUi.Food
    Category.TRANSPORT -> CategoryUi.Transport
    Category.HOUSING -> CategoryUi.Housing
    Category.SHOPPING -> CategoryUi.Shopping
    Category.HEALTH_MEDICAL -> CategoryUi.HealthMedical
    Category.EDUCATION_SELF_DEVELOPMENT -> CategoryUi.EducationSelfDevelopment
    Category.LEISURE_HOBBY -> CategoryUi.LeisureHobby
    Category.SAVING_FINANCE -> CategoryUi.SavingFinance
    Category.OTHER -> CategoryUi.Other
}

private fun PaymentMethod.toUiModel(): PaymentMethodUi = when (this) {
    PaymentMethod.BANK_TRANSFER -> PaymentMethodUi.BankTransfer
    PaymentMethod.CARD -> PaymentMethodUi.Card
    PaymentMethod.CASH -> PaymentMethodUi.Cash
    PaymentMethod.OTHER -> PaymentMethodUi.Other
}

private fun LocalDate.toDisplayMDDate(): String = "${monthValue}월 ${dayOfMonth}일"

private fun LocalDate.toDisplayYMDDate(): String = "${year}년 ${monthValue}월 ${dayOfMonth}일"