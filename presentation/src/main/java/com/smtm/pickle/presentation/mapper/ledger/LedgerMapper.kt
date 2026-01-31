package com.smtm.pickle.presentation.mapper.ledger

import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerCategory
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

fun List<Ledger>.toLedgerCalendarDays(): Map<LocalDate, LedgerCalendarDay> =
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
        .associateBy { it.date }

fun LedgerTypeUi.toDomain(): LedgerType = when (this) {
    LedgerTypeUi.Income -> LedgerType.INCOME
    LedgerTypeUi.Expense -> LedgerType.EXPENSE
}

fun CategoryUi.toDomain(): LedgerCategory = when (this) {
    CategoryUi.Food -> LedgerCategory.Food
    CategoryUi.Transport -> LedgerCategory.Transport
    CategoryUi.Housing -> LedgerCategory.Housing
    CategoryUi.Shopping -> LedgerCategory.Shopping
    CategoryUi.HealthMedical -> LedgerCategory.HealthMedical
    CategoryUi.EducationSelfDevelopment -> LedgerCategory.EducationSelfDevelopment
    CategoryUi.LeisureHobby -> LedgerCategory.LeisureHobby
    CategoryUi.SavingFinance -> LedgerCategory.SavingFinance
    CategoryUi.Salary -> LedgerCategory.Salary
    CategoryUi.SideIncome -> LedgerCategory.SideIncome
    CategoryUi.Bonus -> LedgerCategory.Bonus
    CategoryUi.Allowance -> LedgerCategory.Allowance
    CategoryUi.PartTimeIncome -> LedgerCategory.PartTimeIncome
    CategoryUi.FinancialIncome -> LedgerCategory.FinancialIncome
    CategoryUi.SplitBill -> LedgerCategory.SplitBill
    CategoryUi.Transfer -> LedgerCategory.Transfer
    CategoryUi.Other -> LedgerCategory.Other
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
    LedgerCategory.Food -> CategoryUi.Food
    LedgerCategory.Transport -> CategoryUi.Transport
    LedgerCategory.Housing -> CategoryUi.Housing
    LedgerCategory.Shopping -> CategoryUi.Shopping
    LedgerCategory.HealthMedical -> CategoryUi.HealthMedical
    LedgerCategory.EducationSelfDevelopment -> CategoryUi.EducationSelfDevelopment
    LedgerCategory.LeisureHobby -> CategoryUi.LeisureHobby
    LedgerCategory.SavingFinance -> CategoryUi.SavingFinance
    LedgerCategory.Salary -> CategoryUi.Salary
    LedgerCategory.SideIncome -> CategoryUi.SideIncome
    LedgerCategory.Bonus -> CategoryUi.Bonus
    LedgerCategory.Allowance -> CategoryUi.Allowance
    LedgerCategory.PartTimeIncome -> CategoryUi.PartTimeIncome
    LedgerCategory.FinancialIncome -> CategoryUi.FinancialIncome
    LedgerCategory.SplitBill -> CategoryUi.SplitBill
    LedgerCategory.Transfer -> CategoryUi.Transfer
    LedgerCategory.Other -> CategoryUi.Other
}

private fun PaymentMethod.toUiModel(): PaymentMethodUi = when (this) {
    PaymentMethod.BANK_TRANSFER -> PaymentMethodUi.BankTransfer
    PaymentMethod.CREDIT_CARD -> PaymentMethodUi.CreditCard
    PaymentMethod.DEBIT_CARD -> PaymentMethodUi.DebitCard
    PaymentMethod.CASH -> PaymentMethodUi.Cash
}

private fun LocalDate.toDisplayMDDate(): String = "${monthValue}월 ${dayOfMonth}일"

private fun LocalDate.toDisplayYMDDate(): String = "${year}년 ${monthValue}월 ${dayOfMonth}일"