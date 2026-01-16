package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.remote.api.AuthService
import com.smtm.pickle.data.source.remote.datasource.GoogleAuthDataSource
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.model.auth.SocialLoginType
import com.smtm.pickle.domain.model.auth.User
import com.smtm.pickle.domain.provider.TokenProvider
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val tokenProvider: TokenProvider,
    private val googleAuthDataSource: GoogleAuthDataSource,
) : AuthRepository {

    override suspend fun socialLogin(
        token: String,
        type: SocialLoginType
    ): Result<AuthToken> = runCatching {
        // TODO: 서버 API 연동 시 구현

        Result.success(AuthToken("access", "refresh"))
    } catch (e: Exception) {
        Result.failure(e)
    }
        val mockToken = AuthToken("access", "refresh")

        mockToken
    }

    /** Google 로그인 */
    override suspend fun loginWithGoogle(): Result<AuthToken> =
        googleAuthDataSource.getIdToken().map { idToken ->

            // TODO: 서버로 idToken 전송하여 우리 서버 토큰 받기
            // val serverTokens = authService.signInWithGoogle(idToken).toDomain()
            // tokenDataStore.saveToken(serverTokens)

            val mockToken = AuthToken("access", "refresh")

            mockToken
        }

    override suspend fun getUserInfo(): Result<User> {
        TODO("Not yet implemented")
    }
}
