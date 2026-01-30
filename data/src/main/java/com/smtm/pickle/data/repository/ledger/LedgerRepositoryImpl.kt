package com.smtm.pickle.data.repository.ledger

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.mapper.toEntity
import com.smtm.pickle.data.source.local.database.dao.LedgerDao
import com.smtm.pickle.data.source.remote.api.LedgerApi
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class LedgerRepositoryImpl @Inject constructor(
    private val ledgerDao: LedgerDao,
    private val ledgerApi: LedgerApi,
) : LedgerRepository {

    private val syncedMonths = mutableSetOf<YearMonth>()

    override fun observeLedgers(
        from: LocalDate,
        to: LocalDate,
    ): Flow<List<Ledger>> {
        return ledgerDao.observeByDateRange(
            fromEpochDay = from.toEpochDay(),
            toEpochDay = to.toEpochDay(),
        ).map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun ensureSynced(from: LocalDate, to: LocalDate) {
        val requestedMonths = generateMonthRange(from, to)
        val unsyncedMonths = requestedMonths.filter { it !in syncedMonths }

        if (unsyncedMonths.isEmpty()) return

        val syncFrom = unsyncedMonths.min().atDay(1)
        val syncTo = unsyncedMonths.max().atEndOfMonth()

        val remoteLedgers = ledgerApi.getLedgers(syncFrom, syncTo)
        ledgerDao.insertAll(remoteLedgers)

        syncedMonths.addAll(unsyncedMonths)
    }

    private fun generateMonthRange(from: LocalDate, to: LocalDate): List<YearMonth> {
        val months = mutableListOf<YearMonth>()
        var current = YearMonth.from(from)
        val end = YearMonth.from(to)
        while (current <= end) {
            months.add(current)
            current = current.plusMonths(1)
        }
        return months
    }

    override suspend fun createLedger(ledger: Ledger): LedgerId {
        val insertedId = ledgerDao.insert(ledger.toEntity())
        return LedgerId(insertedId)
    }

    override suspend fun updateLedger(ledger: Ledger) {
        ledgerDao.update(ledger.toEntity())
    }

    override suspend fun deleteLedger(id: LedgerId) {
        ledgerDao.deleteById(id.value)
    }
}