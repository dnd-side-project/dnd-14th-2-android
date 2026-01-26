package com.smtm.pickle.domain.usecase.home

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import java.time.YearMonth
import javax.inject.Inject

class GetLedgersByMonthUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {

    suspend operator fun invoke(yearMonth: YearMonth): List<LedgerEntry> {
        val from = yearMonth.atDay(1)
        val to = yearMonth.atEndOfMonth()
        return ledgerRepository.getLedgers(from, to)
    }
}