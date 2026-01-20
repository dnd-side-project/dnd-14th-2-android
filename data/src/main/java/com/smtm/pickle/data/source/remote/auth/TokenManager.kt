package com.smtm.pickle.data.source.remote.auth

import com.smtm.pickle.data.common.extension.isExpired
import com.smtm.pickle.data.source.remote.api.RefreshTokenApi
import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.provider.TokenProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/*
 * Access 토큰 만료로 401 상황일 때 서버 요청이 여러 번 들어올 경우, 이전 요청이 날아가지 않도록 순서대로 진행
 * - 토큰 갱신은 실패할 수 있는 작업이므로 SupervisorJob()으로 실패해도 TokenManager는 진행
 * - Mutex: 여러 요청이 들어왔을 때 하나 씩 진행 (동기화를 위한 도구)
 *   - withLock: 진행 중이 아니면 실행. 따라서 3번이 요청이 왔을 경우 순서대로 Mutex 진입해 실행
 *         1번 요청 진행 -> refreshJob 실행해 토큰 갱신. 다른 요청은 대기중
 *         2번 요청 진행 -> 1번 요청 종료 시 실행.
 *                         요청이 뒤늦게 왔을 경우 이미 `getAccessToken()`으로 Access가 있는지 판단
 *                         요청이 동시에 왔을 경우 `refreshJob?.let { return it.await() }`으로 실행 대기
 *   access 토큰이 없는 동안 실행된 요청이 모두 완료 시 Authenticator가 새 Request를 만들어 재시도
 */
@Singleton
class TokenManager @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val refreshApi: RefreshTokenApi,
) {
    private var cachedToken: AuthToken? = null

    private var refreshJob: Deferred<AuthToken?>? = null
    private val refreshLock = Mutex()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun init() {
        cachedToken = tokenProvider.getToken()
    }

    suspend fun refreshTokenIfNeeded(): AuthToken? {
        return refreshLock.withLock {

            // Access 코드를 이미 획득했는지 판단
            cachedToken
                ?.takeIf { it.access.isNotBlank() && !it.access.isExpired() }
                ?.let { return it }

            // 이미 refresh 중이면 대기
            refreshJob?.let { return it.await() }

            val job = scope.async {
                // 사용자에게 토큰이 있으면 획득, 없으면
                val refreshToken = cachedToken?.refresh
                    ?: tokenProvider.getRefreshToken()
                    ?: return@async null

                val response = runCatching {
                    refreshApi.refreshToken("Bearer $refreshToken")
                }.getOrNull() ?: run {
                    // 리프레시 토큰 만료시 로그아웃 처리
                    tokenProvider.clearToken()
                    cachedToken = null
                    return@async null
                }

                AuthToken(
                    access = response.accessToken,
                    refresh = response.refreshToken
                ).also {
                    // 토큰 저장
                    tokenProvider.saveToken(it)
                    cachedToken = it
                }
            }

            refreshJob = job
            try {
                job.await()
            } finally {
                refreshLock.withLock {
                    if (refreshJob == job) refreshJob = null
                }
            }
        }
    }

    fun getAccessToken(): String? = cachedToken?.access

    fun getRefreshToken(): String? = cachedToken?.refresh
}
