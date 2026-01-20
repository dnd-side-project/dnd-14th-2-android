package com.smtm.pickle.presentation.verdict.main

import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.presentation.common.PaginatedList

data class VerdictUiState(
    val requestedStatus: VerdictRequestedStatus = VerdictRequestedStatus.None,
    val selectedFilter: VerdictRequestFilter = VerdictRequestFilter.All,
    val isInitialLoading: Boolean = true,
    val allRequests: PaginatedList<VerdictRequest> = PaginatedList(),
    val pendingRequests: PaginatedList<VerdictRequest> = PaginatedList(),
    val completedRequests: PaginatedList<VerdictRequest> = PaginatedList(),
) {
    val currentList: PaginatedList<VerdictRequest>
        get() = getListByFilter(selectedFilter)

    fun getListByFilter(filter: VerdictRequestFilter): PaginatedList<VerdictRequest> {
        return when (filter) {
            VerdictRequestFilter.All -> allRequests
            VerdictRequestFilter.Pending -> pendingRequests
            VerdictRequestFilter.Completed -> completedRequests
        }
    }
}
