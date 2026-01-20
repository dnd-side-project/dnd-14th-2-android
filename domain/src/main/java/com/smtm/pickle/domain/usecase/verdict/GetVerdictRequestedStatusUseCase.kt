package com.smtm.pickle.domain.usecase.verdict

import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.repository.VerdictRepository
import javax.inject.Inject

class GetVerdictRequestedStatusUseCase @Inject constructor(
    private val verdictRepository: VerdictRepository
) {

    suspend operator fun invoke(): Result<VerdictRequestedStatus> {
        return verdictRepository.getRequestedStatus()
    }
}