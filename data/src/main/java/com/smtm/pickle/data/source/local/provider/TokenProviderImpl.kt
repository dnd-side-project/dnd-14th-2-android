package com.smtm.pickle.data.source.local.provider

import com.smtm.pickle.data.source.local.datastore.TokenDataStore
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProviderImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenProvider {
    override suspend fun saveToken(token: AuthToken) {
        tokenDataStore.saveToken(token)
    }

    override suspend fun getToken(): AuthToken? =
        tokenDataStore.getToken()

    override suspend fun getAccessToken(): String? =
        tokenDataStore.getAccessToken()

    override suspend fun getRefreshToken(): String? =
        tokenDataStore.getRefreshToken()

    override suspend fun clearToken() {
        tokenDataStore.clearToken()
    }

    override fun getTokenFlow(): Flow<AuthToken?> =
        tokenDataStore.getTokenFlow()

    override fun getAccessTokenFlow(): Flow<String?> =
        tokenDataStore.getAccessTokenFlow()

    override fun isLoggedInFlow(): Flow<Boolean> =
        tokenDataStore.isLoggedInFlow()
}
