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
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            when {
                error != null -> onFailure(error)
                token != null -> onSuccess(token.accessToken)
                else -> onFailure(Exception(context.getString(R.string.unknown_error)))
            }
        }

        // 카카오톡 로그인 가능 여부에 따라 처리
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                when {
                    token != null -> {
                        onSuccess(token.accessToken)
                    }

                    error != null -> {
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            onFailure(error)
                        } else {
                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                        }
                    }

                    else -> {
                        onFailure(Exception(context.getString(R.string.unknown_error)))
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun logout(onCompleted: () -> Unit) {
        UserApiClient.instance.logout { error ->
            onCompleted()
        }
    }
}
