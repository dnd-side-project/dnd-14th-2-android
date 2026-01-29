package com.smtm.pickle.data.repository.ledger

import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.model.ledger.NewLedgerEntry
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeLedgerRepository @Inject constructor() : LedgerRepository {

    private val idGenerator = AtomicLong(1000L)

    private val fakeLedgers: MutableList<LedgerEntry> = mutableListOf<LedgerEntry>().apply {
        addAll(generateFakeLedgers())
    }

    override fun getLedgers(from: LocalDate, to: LocalDate): List<LedgerEntry> {
        return fakeLedgers.filter { entry ->
            entry.occurredOn in from..to
        }
    }

    override suspend fun createLedger(newLedger: NewLedgerEntry): LedgerId {
        val newId = idGenerator.incrementAndGet()

        val entry = LedgerEntry(
            id = LedgerId(newId),
            type = newLedger.type,
            amount = newLedger.amount,
            category = newLedger.category,
            description = newLedger.description ?: newLedger.category.name,
            occurredOn = newLedger.occurredOn,
            paymentMethod = newLedger.paymentMethod,
            memo = newLedger.memo
        )
        fakeLedgers.add(entry)

        return LedgerId(newId)
    }

    private fun generateFakeLedgers(): List<LedgerEntry> {
        val today = LocalDate.now()
        val entries = mutableListOf<LedgerEntry>()
        var id = 1L

        val startDate = today.minusMonths(6)
        val endDate = today.plusMonths(6)

        var currentDate = startDate
        while (currentDate <= endDate) {
            val countForDay = (0..3).random()
            repeat(countForDay) {
                entries.add(generateRandomEntry(id++, currentDate))
            }
            currentDate = currentDate.plusDays(1)
        }

        return entries
    }

    private fun generateRandomEntry(id: Long, date: LocalDate): LedgerEntry {
        val isIncome = (0..10).random() < 2

        return if (isIncome) {
            LedgerEntry(
                id = LedgerId(id),
                type = LedgerType.INCOME,
                amount = Money(listOf(100_000L, 500_000L, 1_000_000L, 2_500_000L).random()),
                category = LedgerCategory.entries.random(),
                description = listOf("월급", "용돈", "부수입", "이자").random(),
                occurredOn = date,
                paymentMethod = PaymentMethod.entries.random()
            )
        } else {
            LedgerEntry(
                id = LedgerId(id),
                type = LedgerType.EXPENSE,
                amount = Money(listOf(5_000L, 12_000L, 25_000L, 45_000L, 80_000L).random()),
                category = LedgerCategory.entries.random(),
                description = listOf("점심", "커피", "택시", "쇼핑", "마트").random(),
                occurredOn = date,
                paymentMethod = PaymentMethod.entries.random()
            )
        }
    }
}