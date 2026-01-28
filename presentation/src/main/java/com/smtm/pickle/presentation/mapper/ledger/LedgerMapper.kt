package com.smtm.pickle.presentation.mapper.ledger

import com.smtm.pickle.domain.model.ledger.Category
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.presentation.common.utils.toMoneyFormat
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.home.model.DailyLedgerUi
import com.smtm.pickle.presentation.home.model.LedgerTypeUi
import com.smtm.pickle.presentation.home.model.LedgerUi
import com.smtm.pickle.presentation.home.model.PaymentMethodUi
import java.time.LocalDate

fun LedgerEntry.toUiModel(): LedgerUi = LedgerUi(
    id = id.value,
    type = type.toUiModel(),
    amount = amount.value.toMoneyFormat(),
    amountValue = amount.value,
    category = category.toUiModel(),
    description = description,
    occurredOn = occurredOn,
    dateText = occurredOn.toDisplayYMDDate(),
    paymentMethod = paymentMethod?.toUiModel() ?: PaymentMethodUi.Cash,
    memo = memo
)

fun List<LedgerEntry>.toDailyLedgerUiModel(): List<DailyLedgerUi> =
    groupBy { it.occurredOn }
        .map { (date, entries) ->
            DailyLedgerUi(
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

fun LedgerTypeUi.toDomain(): LedgerType = when (this) {
    LedgerTypeUi.Income -> LedgerType.INCOME
    LedgerTypeUi.Expense -> LedgerType.EXPENSE
}

fun CategoryUi.toDomain(): Category = when (this) {
    CategoryUi.Food -> Category.FOOD
    CategoryUi.Transport -> Category.TRANSPORT
    CategoryUi.Housing -> Category.HOUSING
    CategoryUi.Shopping -> Category.SHOPPING
    CategoryUi.HealthMedical -> Category.HEALTH_MEDICAL
    CategoryUi.EducationSelfDevelopment -> Category.EDUCATION_SELF_DEVELOPMENT
    CategoryUi.LeisureHobby -> Category.LEISURE_HOBBY
    CategoryUi.SavingFinance -> Category.SAVING_FINANCE
    CategoryUi.Other -> Category.OTHER
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
    PaymentMethod.CREDIT_CARD -> PaymentMethodUi.CreditCard
    PaymentMethod.DEBIT_CARD -> PaymentMethodUi.DebitCard
    PaymentMethod.CASH -> PaymentMethodUi.Cash
}

private fun LocalDate.toDisplayMDDate(): String = "${monthValue}월 ${dayOfMonth}일"

private fun LocalDate.toDisplayYMDDate(): String = "${year}년 ${monthValue}월 ${dayOfMonth}일"