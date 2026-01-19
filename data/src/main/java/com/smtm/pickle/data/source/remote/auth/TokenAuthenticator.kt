package com.smtm.pickle.data.source.remote.auth

import com.smtm.pickle.data.source.remote.api.RefreshTokenApi
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val refreshApi: RefreshTokenApi,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (responseCount(response) >= 2) return null

        // RefreshToken으로 새로운 Token 발급
        val newToken = runBlocking {
            val refreshToken = tokenProvider.getRefreshToken() ?: return@runBlocking null

            runCatching {
                refreshApi.refreshToken("Bearer $refreshToken")
            }.getOrNull()
        } ?: run {
            // 리프레시 토큰 만료시 로그아웃 처리
            runBlocking { tokenProvider.clearToken() }
            return null
        }

        // 토큰 저장
        runBlocking {
            tokenProvider.saveToken(
                AuthToken(
                    access = newToken.accessToken,
                    refresh = newToken.refreshToken
                )
            )
        }

        // 401 엑세스 토큰 만료로 실패한 요청에 새 토큰을 넣어 재시도
        return response.request.newBuilder()
            .header("Authorization", "Bearer ${newToken.accessToken}")
            .build()
    }

    // 순환 참조 방지
    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse

        while (prior != null) {
            count++
            prior = prior.priorResponse
        }

        return count
    }
}
