package com.smtm.pickle.domain.usecase.ledger.create

import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.NewLedgerEntry
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class CreateLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository
) {
    suspend operator fun invoke(newLedger: NewLedgerEntry): LedgerId {
        return ledgerRepository.createLedger(newLedger)
    }
}