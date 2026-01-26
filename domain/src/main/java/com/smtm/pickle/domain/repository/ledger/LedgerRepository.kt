package com.smtm.pickle.domain.repository.ledger

import com.smtm.pickle.domain.model.ledger.LedgerEntry
import java.time.LocalDate

interface LedgerRepository {

    /**
     * 지정된 날짜 범위의 가계부 항목을 조회한다.
     * @param from 시작일
     * @param to 종료일
     */
    fun getLedgers(from: LocalDate, to: LocalDate): List<LedgerEntry>
}