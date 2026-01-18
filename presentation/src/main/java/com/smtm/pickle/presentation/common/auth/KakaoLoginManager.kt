package com.smtm.pickle.presentation.common.auth

import android.content.Context
import androidx.compose.runtime.Stable
import com.kakao.sdk.auth.model.OAuthToken
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
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
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
