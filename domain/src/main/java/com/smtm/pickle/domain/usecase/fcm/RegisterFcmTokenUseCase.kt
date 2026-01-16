package com.smtm.pickle.domain.usecase.fcm

import com.smtm.pickle.domain.model.FcmTokenInfo
import com.smtm.pickle.domain.repository.FcmTokenRepository
import javax.inject.Inject

class RegisterFcmTokenUseCase @Inject constructor(
    private val fcmTokenRepository: FcmTokenRepository
) {

    suspend operator fun invoke(token: String): Result<Unit> {
        val tokenInfo = FcmTokenInfo(token = token)
        return fcmTokenRepository.registerFcmToken(tokenInfo)
            .onSuccess { fcmTokenRepository.saveLastUpdateTime(tokenInfo.updatedAt) }
    }
}