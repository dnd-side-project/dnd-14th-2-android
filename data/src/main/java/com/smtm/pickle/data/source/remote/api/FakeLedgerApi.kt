package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.local.database.entity.LedgerEntity
import java.time.LocalDate
import javax.inject.Inject

class FakeLedgerApi @Inject constructor() : LedgerApi {

    override suspend fun getLedgers(
        from: LocalDate,
        to: LocalDate,
    ): List<LedgerEntity> {
        var id = 1L
        val entries = mutableListOf<LedgerEntity>()
        var currentDate = from
        while (currentDate <= to) {
            val countForDay = (0..3).random()
            repeat(countForDay) {
                entries.add(generateRandomEntity(id++, currentDate))
            }
            currentDate = currentDate.plusDays(1)
        }
        return entries
    }

    private fun generateRandomEntity(id: Long, date: LocalDate): LedgerEntity {
        val isIncome = (0..10).random() < 2
        return if (isIncome) {
            LedgerEntity(
                id = id,
                type = "INCOME",
                amount = listOf(100_000L, 500_000L, 1_000_000L, 2_500_000L).random(),
                category = listOf("FOOD", "SAVING_FINANCE", "OTHER").random(),
                description = listOf("월급", "용돈", "부수입", "이자").random(),
                occurredOn = date.toEpochDay(),
                paymentMethod = listOf("BANK_TRANSFER", "CREDIT_CARD", "CASH").random(),
                memo = null,
            )
        } else {
            LedgerEntity(
                id = id,
                type = "EXPENSE",
                amount = listOf(5_000L, 12_000L, 25_000L, 45_000L, 80_000L).random(),
                category = listOf("FOOD", "TRANSPORT", "SHOPPING", "LEISURE_HOBBY").random(),
                description = listOf("점심", "커피", "택시", "쇼핑", "마트").random(),
                occurredOn = date.toEpochDay(),
                paymentMethod = listOf("CREDIT_CARD", "CASH", "DEBIT_CARD").random(),
                memo = null,
            )
        }
    }
}