package com.smtm.pickle.presentation.mapper.ledger

import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.LedgerCalendarDay
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.LedgerUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import java.time.LocalDate

fun Ledger.toUiModel(): LedgerUi = LedgerUi(
    id = id.value,
    type = type.toUiModel(),
    amount = amount.value.toMoneyFormat(),
    amountValue = amount.value,
    category = category.toUiModel(),
    description = description,
    occurredOn = occurredOn,
    dateText = occurredOn.toDisplayYMDDate(),
    paymentMethod = paymentMethod.toUiModel(),
    memo = memo
)

fun List<Ledger>.toLedgerCalendarDays(): List<LedgerCalendarDay> =
    groupBy { it.occurredOn }
        .map { (date, ledgers) ->
            val dayTotalIncome = ledgers
                .filter { it.type == LedgerType.INCOME }
                .sumOf { it.amount.value }
                .takeIf { it > 0 }
            val dayTotalExpense = ledgers
                .filter { it.type == LedgerType.EXPENSE }
                .sumOf { it.amount.value }
                .takeIf { it > 0 }
            LedgerCalendarDay(
                date = date,
                totalIncome = dayTotalIncome,
                totalExpense = dayTotalExpense,
            )
        }
        .sortedByDescending { it.date }

fun LedgerTypeUi.toDomain(): LedgerType = when (this) {
    LedgerTypeUi.Income -> LedgerType.INCOME
    LedgerTypeUi.Expense -> LedgerType.EXPENSE
}

fun CategoryUi.toDomain(): LedgerCategory = when (this) {
    CategoryUi.Food -> LedgerCategory.FOOD
    CategoryUi.Transport -> LedgerCategory.TRANSPORT
    CategoryUi.Housing -> LedgerCategory.HOUSING
    CategoryUi.Shopping -> LedgerCategory.SHOPPING
    CategoryUi.HealthMedical -> LedgerCategory.HEALTH_MEDICAL
    CategoryUi.EducationSelfDevelopment -> LedgerCategory.EDUCATION_SELF_DEVELOPMENT
    CategoryUi.LeisureHobby -> LedgerCategory.LEISURE_HOBBY
    CategoryUi.SavingFinance -> LedgerCategory.SAVING_FINANCE
    CategoryUi.Other -> LedgerCategory.OTHER
}

fun PaymentMethodUi.toDomain(): PaymentMethod = when (this) {
    PaymentMethodUi.CreditCard -> PaymentMethod.CREDIT_CARD
    PaymentMethodUi.DebitCard -> PaymentMethod.DEBIT_CARD
    PaymentMethodUi.Cash -> PaymentMethod.CASH
    PaymentMethodUi.BankTransfer -> PaymentMethod.BANK_TRANSFER
}

private fun LedgerType.toUiModel(): LedgerTypeUi = when (this) {
    LedgerType.INCOME -> LedgerTypeUi.Income
    LedgerType.EXPENSE -> LedgerTypeUi.Expense
}

private fun LedgerCategory.toUiModel(): CategoryUi = when (this) {
    LedgerCategory.FOOD -> CategoryUi.Food
    LedgerCategory.TRANSPORT -> CategoryUi.Transport
    LedgerCategory.HOUSING -> CategoryUi.Housing
    LedgerCategory.SHOPPING -> CategoryUi.Shopping
    LedgerCategory.HEALTH_MEDICAL -> CategoryUi.HealthMedical
    LedgerCategory.EDUCATION_SELF_DEVELOPMENT -> CategoryUi.EducationSelfDevelopment
    LedgerCategory.LEISURE_HOBBY -> CategoryUi.LeisureHobby
    LedgerCategory.SAVING_FINANCE -> CategoryUi.SavingFinance
    LedgerCategory.OTHER -> CategoryUi.Other
}

private fun PaymentMethod.toUiModel(): PaymentMethodUi = when (this) {
    PaymentMethod.BANK_TRANSFER -> PaymentMethodUi.BankTransfer
    PaymentMethod.CREDIT_CARD -> PaymentMethodUi.CreditCard
    PaymentMethod.DEBIT_CARD -> PaymentMethodUi.DebitCard
    PaymentMethod.CASH -> PaymentMethodUi.Cash
}

private fun LocalDate.toDisplayMDDate(): String = "${monthValue}월 ${dayOfMonth}일"

private fun LocalDate.toDisplayYMDDate(): String = "${year}년 ${monthValue}월 ${dayOfMonth}일"