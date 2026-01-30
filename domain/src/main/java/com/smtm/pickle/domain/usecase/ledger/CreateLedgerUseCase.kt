package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class CreateLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {
    suspend operator fun invoke(ledger: LedgerEntry): LedgerId {
        return ledgerRepository.createLedger(ledger)
    }
}