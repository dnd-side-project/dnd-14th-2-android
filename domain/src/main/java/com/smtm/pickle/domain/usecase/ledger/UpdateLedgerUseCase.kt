package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.ledger.Ledger
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class UpdateLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(ledger: Ledger): Result<Unit> = runSuspendCatching {
        ledgerRepository.updateLedger(ledger)
    }
}