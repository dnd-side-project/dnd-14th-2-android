package com.smtm.pickle.domain.model

data class FcmTokenInfo(
    val token: String,
    val updatedAt: Long = System.currentTimeMillis()
)