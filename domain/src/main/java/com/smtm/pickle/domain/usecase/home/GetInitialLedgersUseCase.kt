package com.smtm.pickle.domain.usecase.home

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import java.time.YearMonth
import javax.inject.Inject

class GetInitialLedgersUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke(
        baseMonth: YearMonth = YearMonth.now(),
        monthsBack: Long = 6,
        monthForward: Long = 6,
    ): List<LedgerEntry> {
        val from = baseMonth.minusMonths(monthsBack).atDay(1)
        val to = baseMonth.plusMonths(monthForward).atEndOfMonth()
        return ledgerRepository.getLedgers(from, to)
    }
}