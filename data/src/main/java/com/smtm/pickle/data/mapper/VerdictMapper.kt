package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.VerdictRequestDto
import com.smtm.pickle.data.source.remote.model.VerdictRequestsResponse
import com.smtm.pickle.data.source.remote.model.VerdictStatusResponse
import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.model.verdict.VerdictStatus

fun VerdictRequestsResponse.toDomain(): PaginatedResult<VerdictRequest> {
    return PaginatedResult(
        items = content.map { it.toDomain() },
        hasMore = hasNext,
        totalCount = totalElements
    )
}

fun VerdictRequestDto.toDomain(): VerdictRequest {
    return VerdictRequest(
        id = id,
        userId = userId,
        nickname = nickname,
        badge = badge,
        profileImage = profileImage,
        status = when (status) {
            "PENDING" -> VerdictStatus.Pending
            "COMPLETED" -> VerdictStatus.Completed
            else -> VerdictStatus.Pending
        },
        joinedCount = joinedCount
    )
}

fun VerdictStatusResponse.toDomain(): VerdictRequestedStatus {
    return if (hasNewRequest) {
        VerdictRequestedStatus.Requested
    } else {
        VerdictRequestedStatus.None
    }
}