package com.smtm.pickle.data.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val token: String,      // GOOGLE or KAKAO
    val loginType: String,  // GOOGLE: IdToken, KAKAO: Auth Token
)
