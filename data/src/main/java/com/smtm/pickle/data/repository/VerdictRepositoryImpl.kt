package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.source.remote.api.VerdictService
import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerdictRepositoryImpl @Inject constructor(
    private val verdictService: VerdictService
): VerdictRepository{

    override suspend fun getVerdictRequests(
        filter: VerdictRequestFilter,
        page: Int,
        size: Int
    ): Result<PaginatedResult<VerdictRequest>> {
        return runCatching {
            val response = verdictService.getVerdictRequests(
                status = filter.name,
                page = page,
                size = size
            )
            response.toDomain()
        }
    }

    override suspend fun getRequestedStatus(): Result<VerdictRequestedStatus> {
        return runCatching {
            val response = verdictService.getRequestedStatus()
            response.toDomain()
        }
    }
}