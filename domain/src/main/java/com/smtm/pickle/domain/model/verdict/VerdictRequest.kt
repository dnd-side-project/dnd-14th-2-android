package com.smtm.pickle.domain.model.verdict

data class VerdictRequest(
    val id: Long,
    val userId: String,
    val nickname: String,
    val badge: String,
    val profileImage: String,
    val status: VerdictStatus,
    val joinedCount: Int,
)

enum class VerdictRequestedStatus {
    Requested,
    None
}

enum class VerdictStatus {
    Pending,
    Completed
}

enum class VerdictRequestFilter {
    All,
    Pending,
    Completed
}