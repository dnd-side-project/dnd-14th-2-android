package com.smtm.pickle.domain.usecase.ledger

import com.smtm.pickle.domain.common.utils.runSuspendCatching
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import javax.inject.Inject

class DeleteLedgerUseCase @Inject constructor(
    private val ledgerRepository: LedgerRepository,
) {

    suspend operator fun invoke(id: LedgerId): Result<Unit> = runSuspendCatching {
        ledgerRepository.deleteLedger(id)
    }
}