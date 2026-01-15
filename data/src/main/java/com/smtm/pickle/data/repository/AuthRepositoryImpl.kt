package com.smtm.pickle.data.repository

import com.smtm.pickle.data.mapper.toDomain
import com.smtm.pickle.data.source.local.datastore.TokenDataStore
import com.smtm.pickle.data.source.remote.api.AuthService
import com.smtm.pickle.data.source.remote.model.LoginRequest
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.model.auth.SocialLoginType
import com.smtm.pickle.domain.model.auth.User
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun socialLogin(
        token: String,
        type: SocialLoginType
    ): Result<AuthToken> = try {
        // TODO: 구현 필요

        Result.success(AuthToken("access", "refresh"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun saveToken(token: AuthToken) {
        tokenDataStore.saveToken(token)
    }

    override suspend fun getToken(): AuthToken? =
        tokenDataStore.getToken()

    override suspend fun clearToken() {
        tokenDataStore.clearToken()
    }

    override suspend fun getUserInfo(): Result<User> {
        TODO("Not yet implemented")
    }
}
