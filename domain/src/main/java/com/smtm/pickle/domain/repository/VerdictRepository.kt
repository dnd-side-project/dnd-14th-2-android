package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus

interface VerdictRepository {

    /**
     * 심판 요청 목록 조회 (페이지네이션)
     *
     * @param filter 필터 (전체/대기중/완료)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기 (기본 10)
     * @return 페이지네이션된 심판 요청 목록
     */
    suspend fun getVerdictRequests(
        filter: VerdictRequestFilter,
        page: Int,
        size: Int,
    ): Result<PaginatedResult<VerdictRequest>>

    /**
     * 심판 요청 도착 상태 조회
     *
     * @return 새로운 심판 요청이 있는지 여부
     */
    suspend fun getRequestedStatus(): Result<VerdictRequestedStatus>
}