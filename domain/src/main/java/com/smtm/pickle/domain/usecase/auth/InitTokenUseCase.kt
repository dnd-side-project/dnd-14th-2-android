package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class InitTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.initToken()
    }
}
