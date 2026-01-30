package com.smtm.pickle.domain.repository.ledger

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerId
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface LedgerRepository {

    fun observeLedgers(from: LocalDate, to: LocalDate): Flow<List<LedgerEntry>>

    // 동기화: 외부 데이터 -> Room 캐시 보장
    suspend fun ensureSynced(from: LocalDate, to: LocalDate)

    suspend fun createLedger(ledger: LedgerEntry): LedgerId
    suspend fun updateLedger(ledger: LedgerEntry)
    suspend fun deleteLedger(ledger: LedgerId)
}