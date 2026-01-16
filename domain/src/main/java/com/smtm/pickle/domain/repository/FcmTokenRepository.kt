package com.smtm.pickle.domain.repository

import com.smtm.pickle.domain.model.FcmTokenInfo

interface FcmTokenRepository {

    /** 서버에 토큰 등록 (로그인 시, onNewToken 시) */
    suspend fun registerFcmToken(tokenInfo: FcmTokenInfo): Result<Unit>

    /** 서버에서 토큰 해제 (로그아웃 시) */
    suspend fun unregisterFcmToken(): Result<Unit>

    /** 마지막 토큰 갱신 시간 조회 (로컬) */
    suspend fun getLastUpdateTime(): Long?

    /** 마지막 토큰 갱신 시간 저장 (로컬) */
    suspend fun saveLastUpdateTime(time: Long)
}