package com.smtm.pickle.domain.usecase.fcm

import com.smtm.pickle.domain.repository.FcmTokenRepository
import javax.inject.Inject

class UnregisterFcmTokenUseCase @Inject constructor(
    private val userRepository: FcmTokenRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        return userRepository.unregisterFcmToken()
    }
}