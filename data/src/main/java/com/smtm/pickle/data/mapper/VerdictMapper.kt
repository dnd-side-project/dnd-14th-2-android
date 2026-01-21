package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.remote.model.VerdictRequestDto
import com.smtm.pickle.data.source.remote.model.VerdictRequestsResponse
import com.smtm.pickle.data.source.remote.model.VerdictStatusResponse
import com.smtm.pickle.domain.model.common.PaginatedResult
import com.smtm.pickle.domain.model.verdict.PaymentMethod
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
        myNickname = myNickname,
        jurorId = jurorId,
        jurorNickname = jurorNickname,
        jurorBadge = jurorBadge,
        jurorProfileImage = jurorProfileImage,
        status = when (status) {
            "Pending" -> VerdictStatus.Pending
            "Completed" -> VerdictStatus.Completed
            else -> VerdictStatus.Pending
        },
        joinedCount = joinedCount,
        spendingTitle = spendingTitle,
        spendingCategory = spendingCategory,
        paymentMethod = when (paymentMethod) {
            "Cash" -> PaymentMethod.Cash
            "Card" -> PaymentMethod.Card
            else -> PaymentMethod.Cash
        },
        spendingReview = spendingReview,
    )
}

fun VerdictStatusResponse.toDomain(): VerdictRequestedStatus {
    return if (hasNewRequest) {
        VerdictRequestedStatus.Requested
    } else {
        VerdictRequestedStatus.None
    }
}