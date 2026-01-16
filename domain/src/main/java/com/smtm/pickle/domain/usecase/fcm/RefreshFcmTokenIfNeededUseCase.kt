package com.smtm.pickle.domain.usecase.fcm

import com.smtm.pickle.domain.model.FcmTokenInfo
import com.smtm.pickle.domain.repository.FcmTokenRepository
import javax.inject.Inject

class RefreshFcmTokenIfNeededUseCase @Inject constructor(
    private val fcmTokenRepository: FcmTokenRepository
) {

    suspend operator fun invoke(currentToken: String): Result<Unit> {
        val lastUpdate = fcmTokenRepository.getLastUpdateTime() ?: 0L
        val now = System.currentTimeMillis()
        val thirtyDays = 30L * 24 * 60 * 60 * 1000

        if (now - lastUpdate > thirtyDays) {
            val tokenInfo = FcmTokenInfo(token = currentToken, updatedAt = now)
            return fcmTokenRepository.registerFcmToken(tokenInfo)
                .onSuccess { fcmTokenRepository.saveLastUpdateTime(now) }
        }

        return Result.success(Unit)
    }
}