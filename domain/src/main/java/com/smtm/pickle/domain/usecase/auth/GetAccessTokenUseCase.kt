package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): String? = authRepository.getAccessToken()
}

