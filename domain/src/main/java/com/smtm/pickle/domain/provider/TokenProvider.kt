package com.smtm.pickle.domain.provider

import com.smtm.pickle.domain.model.auth.AuthToken
import kotlinx.coroutines.flow.Flow

/**
 * 토큰 관리 인터페이스
 * TokenDataStore를 래핑하는 역할
 */
interface TokenProvider {

    suspend fun saveToken(token: AuthToken)

    suspend fun getToken(): AuthToken?

    suspend fun getAccessToken(): String?

    suspend fun getRefreshToken(): String?

    suspend fun clearToken()

    fun getTokenFlow(): Flow<AuthToken?>

    fun getAccessTokenFlow(): Flow<String?>

    fun isLoggedInFlow(): Flow<Boolean>
}
