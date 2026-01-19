package com.smtm.pickle.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val token: String,
    val loginType: String,
)
