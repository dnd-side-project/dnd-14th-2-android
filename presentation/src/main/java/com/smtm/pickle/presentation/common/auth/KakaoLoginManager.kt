package com.smtm.pickle.presentation.common.auth

import android.content.Context
import androidx.compose.runtime.Stable
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.smtm.pickle.presentation.R
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@Stable
@ActivityScoped
class KakaoLoginManager @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun login(
        onSuccess: (String) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val resultHandler: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                token?.idToken != null -> {
                    onSuccess(token.idToken!!)
                }

                error != null -> {
                    onFailure(error)
                }

                else -> {
                    onFailure(IllegalStateException(context.getString(R.string.unknown_error)))
                }
            }
        }

        if (!UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoAccount(
                context = context,
                callback = resultHandler
            )
            return
        }

        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            when {
                token?.idToken != null -> {
                    onSuccess(token.idToken!!)
                }

                // 사용자 취소도 로그인 실패 처리
                error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                    onFailure(error)
                }

                // 카카오톡 실패 → 카카오계정 로그인 fallback
                error != null -> {
                    UserApiClient.instance.loginWithKakaoAccount(
                        context = context,
                        callback = resultHandler
                    )
                }

                else -> {
                    onFailure(IllegalStateException(context.getString(R.string.unknown_error)))
                }
            }
        }
    }

    fun logout(onCompleted: () -> Unit) {
        UserApiClient.instance.logout { error ->
            onCompleted()
        }
    }
}
