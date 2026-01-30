package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.local.database.entity.LedgerEntity
import java.time.LocalDate

interface LedgerApi {

    suspend fun getLedgers(from: LocalDate, to: LocalDate): List<LedgerEntity>
}