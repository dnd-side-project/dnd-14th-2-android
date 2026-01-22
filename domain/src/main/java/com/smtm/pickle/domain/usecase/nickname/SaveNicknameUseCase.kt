package com.smtm.pickle.domain.usecase.nickname

import com.smtm.pickle.domain.repository.NicknameRepository
import javax.inject.Inject

class SaveNicknameUseCase @Inject constructor(
    private val nicknameRepository: NicknameRepository
) {
    suspend operator fun invoke(nickname: String) {
        nicknameRepository.saveNickname(nickname)
    }
}
