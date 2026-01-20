package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class GetVerdictRequestsUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository
) {

    /**
     * @param filter 필터 타입
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     */
    suspend operator fun invoke(
        filter: VerdictRequestFilter,
        page: Int,
        size: Int = DEFAULT_PAGE_SIZE
    ): Result<PaginatedResult<VerdictRequest>> {
        return verdictRepository.getVerdictRequests(filter, page, size)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}