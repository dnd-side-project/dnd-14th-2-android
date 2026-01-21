package com.smtm.pickle.data.source.remote.model

import com.smtm.pickle.domain.model.verdict.PaymentMethod
import kotlinx.serialization.Serializable

@Serializable
data class VerdictRequestsResponse(
    val content: List<VerdictRequestDto>,
    val hasNext: Boolean,
    val totalElements: Int = 0
)

@Serializable
data class VerdictRequestDto(
    val id: Long,
    val myNickname: String,             // 사용자 닉네임
    val jurorId: String,                // 배심원 id
    val jurorNickname: String,          // 배심원 닉네임
    val jurorBadge: String,             // 배심원 뱃지
    val jurorProfileImage: String,      // 배심원 프로필 이미지
    val status: String,                 // 심판 진행 상태
    val joinedCount: Int,               // 함께한 심판 수
    val spendingTitle: String,          // 소비내역 제목
    val spendingCategory: String,       // 소비 카테고리
    val paymentMethod: String,   // 결제수단
    val spendingReview: String?,        // 소비 회고
)

@Serializable
data class VerdictStatusResponse(
    val hasNewRequest: Boolean,
    val pendingCount: Int? = 0
)


