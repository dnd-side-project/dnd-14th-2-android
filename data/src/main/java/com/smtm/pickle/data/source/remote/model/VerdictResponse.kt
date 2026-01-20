package com.smtm.pickle.data.source.remote.model

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
    val userId: String,
    val nickname: String,
    val badge: String,
    val profileImage: String,
    val status: String,
    val joinedCount: Int
)

@Serializable
data class VerdictStatusResponse(
    val hasNewRequest: Boolean,
    val pendingCount: Int? = 0
)


