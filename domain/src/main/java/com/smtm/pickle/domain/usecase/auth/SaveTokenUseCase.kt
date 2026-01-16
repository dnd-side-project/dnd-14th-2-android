package com.smtm.pickle.domain.usecase.auth

import com.smtm.pickle.domain.model.auth.AuthToken
import com.smtm.pickle.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(token: AuthToken) {
        authRepository.saveToken(token)
    }
}
