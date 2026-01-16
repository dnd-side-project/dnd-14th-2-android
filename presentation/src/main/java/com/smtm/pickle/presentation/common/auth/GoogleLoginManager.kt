package com.smtm.pickle.presentation.common.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.smtm.pickle.presentation.BuildConfig
import com.smtm.pickle.presentation.R
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

class GoogleLoginManager @Inject constructor(
    @ActivityContext private val context: Context
) {
    private val googleSignInClient = CredentialManager.create(context)

    /**
     * Google 로그인 시작
     * @param scope CoroutineScope (lifecycleScope 사용 권장)
     * @param onSuccess 성공 콜백 - ID 토큰
     * @param onFailure 실패 콜백
     */
    fun login(
        scope: CoroutineScope,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        scope.launch {
            try {
                // 구글 로그인 창이 뜰 때 옵션 설정
                val googleIdOption = GetGoogleIdOption.Builder()
                    // false: 사용자의 기기에 등록된 모든 ID 보여줌
                    // true: 이전 이 앱 로그인 한 계정만
                    .setFilterByAuthorizedAccounts(false)
                    // 사용자 검증을 위한 ID 토큰 발급
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    // 재전송 공격 방지 일회성 암호표
                    .setNonce(generateNonce())
                    .build()

                // 패스키나 일반 로그인 옵션 추가
                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption) // 구글 로그인 옵션만
                    .build()

                // 구글 로그인 바텀 시트 호출
                val result = googleSignInClient.getCredential(context = context, request = request)

                // ID 토큰 추출
                handleSignInResult(result, onSuccess, onFailure)

            } catch (e: GetCredentialException) {
                // 사용자 취소 또는 오류난 경우
                onFailure(e)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }

    /** 로그인 결과 처리 */
    private fun handleSignInResult(
        result: GetCredentialResponse,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        when (val credential = result.credential) {

            // CustomCredential: 구글 로그인은 표준이 아닌 커스텀 인증 방식
            is CustomCredential -> {

                // 현재 설정된 구글 로그인 요청에 대해서 처리
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // credential 객체에서 ID 토큰 추출
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        // 서버 전송용 구글 고유 식별자(사용자 검증용)
                        val idToken = googleIdTokenCredential.idToken

                        onSuccess(idToken)
                    } catch (e: Exception) {
                        onFailure(e)
                    }
                } else {
                    onFailure(Exception(context.getString(R.string.invaild_credential_type)))
                }
            }

            else -> {
                onFailure(Exception(context.getString(R.string.invaild_credential_type)))
            }
        }
    }

    /** 보안 강화용 Nonce 생성 - 재사용 공격 방지 */
    private fun generateNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val digest = messageDigest.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    /** 저장된 계정으로 자동 로그인 */
    fun autoLogin(
        scope: CoroutineScope,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        scope.launch {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = googleSignInClient.getCredential(context = context, request = request)

                handleSignInResult(result, onSuccess, onFailure)

            } catch (e: GetCredentialException) {
                // 저장된 계정이 없을 경우 - 일반 로그인 진행
                onFailure(e)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}
