package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth
import javax.inject.Inject

class ObserveLedgersByMonthUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    operator fun invoke(yearMonth: YearMonth): Flow<List<LedgerEntry>> {
        val from = yearMonth.atDay(1)
        val to = yearMonth.atEndOfMonth()
        return ledgerRepository.observeLedgers(from, to)
    }
}