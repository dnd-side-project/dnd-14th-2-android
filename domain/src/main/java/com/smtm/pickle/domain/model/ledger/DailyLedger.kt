package com.smtm.pickle.domain.model.ledger

import java.time.LocalDate

data class Income(
    val id: Long,
    val title: String,
    val amount: Long,
    val category: String,
)

data class Expense(
    val id: Long,
    val title: String,
    val amount: Long,
    val category: String
)

data class DailyLedger(
    val date: LocalDate,
    val incomes: List<Income> = emptyList(),
    val expenses: List<Expense> = emptyList(),
) {
    val totalIncome: Long get() = incomes.sumOf { it.amount }
    val totalExpense: Long get() = expenses.sumOf { it.amount }
    val balance: Long get() = totalIncome - totalExpense
}