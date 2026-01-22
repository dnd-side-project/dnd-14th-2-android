package com.smtm.pickle.domain.usecase.home

import com.smtm.pickle.domain.model.ledger.DailyLedger
import com.smtm.pickle.domain.model.ledger.Expense
import com.smtm.pickle.domain.model.ledger.Income
import java.time.LocalDate
import javax.inject.Inject

class GetDailyLedgersUseCase @Inject constructor(

) {
    suspend operator fun invoke(): List<DailyLedger> {
        val dailyLedgerList = listOf(
            DailyLedger(
                date = LocalDate.now(),
                incomes = listOf(
                    Income(
                        id = 1L,
                        title = "월급",
                        amount = 2_500_000,
                        category = "급여"
                    )
                ),
                expenses = listOf(
                    Expense(
                        id = 2L,
                        title = "점심",
                        amount = 12_000,
                        category = "식비"
                    ),
                    Expense(
                        id = 3L,
                        title = "커피",
                        amount = 4_500,
                        category = "카페"
                    )
                )
            ),

            DailyLedger(
                date = LocalDate.now().minusDays(1),
                expenses = listOf(
                    Expense(
                        id = 4L,
                        title = "저녁",
                        amount = 35_000,
                        category = "식비"
                    )
                )
            ),

            DailyLedger(
                date = LocalDate.now().minusDays(3),
                incomes = listOf(
                    Income(
                        id = 5L,
                        title = "용돈",
                        amount = 100_000,
                        category = "기타수입"
                    )
                )
            ),

            DailyLedger(
                date = LocalDate.now().plusDays(2),
                expenses = listOf(
                    Expense(
                        id = 6L,
                        title = "마트",
                        amount = 85_000,
                        category = "생활비"
                    )
                )
            )
        )
        return dailyLedgerList
    }
}