package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class ObserveLedgersByDayUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    operator fun invoke(date: LocalDate): Flow<List<LedgerEntry>> {
        return ledgerRepository.observeLedgers(date, date)
    }
}