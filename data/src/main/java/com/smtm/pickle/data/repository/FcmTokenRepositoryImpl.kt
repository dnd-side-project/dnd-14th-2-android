package com.smtm.pickle.data.repository

import com.smtm.pickle.domain.model.FcmTokenInfo
import com.smtm.pickle.domain.repository.FcmTokenRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FcmTokenRepositoryImpl @Inject constructor(

) : FcmTokenRepository {

    override suspend fun registerFcmToken(tokenInfo: FcmTokenInfo): Result<Unit> {
        Timber.d("FCM Token 등록 요청: ${tokenInfo.token}, 시간: ${tokenInfo.updatedAt}")
        return Result.success(Unit)
    }

    override suspend fun unregisterFcmToken(): Result<Unit> {
        Timber.d("FCM Token 해제 요청")
        return Result.success(Unit)
    }

    override suspend fun getLastUpdateTime(): Long? {
        return 0L
    }

    override suspend fun saveLastUpdateTime(time: Long) {
        Timber.d("FCM Token 갱신 시간 저장: $time")
    }
}