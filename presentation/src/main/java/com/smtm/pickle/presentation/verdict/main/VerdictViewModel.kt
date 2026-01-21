package com.smtm.pickle.presentation.verdict.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.usecase.verdict.GetVerdictRequestedStatusUseCase
import com.smtm.pickle.domain.usecase.verdict.GetVerdictRequestsUseCase
import com.smtm.pickle.presentation.common.PaginatedList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerdictViewModel @Inject constructor(
    private val getVerdictRequestedStatusUseCase: GetVerdictRequestedStatusUseCase,
    private val getVerdictRequestsUseCase: GetVerdictRequestsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerdictUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRequestedStatus()
        loadInitialData()
    }

    fun loadRequestedStatus() {
        viewModelScope.launch {
            getVerdictRequestedStatusUseCase().fold(
                onSuccess = { status ->
                    _uiState.update { state ->
                        state.copy(requestedStatus = status)
                    }
                },
                onFailure = {
                    _uiState.update { state ->
                        state.copy(requestedStatus = VerdictRequestedStatus.None)
                    }
                }
            )
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isInitialLoading = true) }
            val requestedStatusDeferred = async { getVerdictRequestedStatusUseCase() }
            val allDeferred = async { loadFirstPage(VerdictRequestFilter.All) }
            val pendingDeferred = async { loadFirstPage(VerdictRequestFilter.Pending) }
            val completedDeferred = async { loadFirstPage(VerdictRequestFilter.Completed) }

            val requestedStatus = requestedStatusDeferred.await().getOrDefault(VerdictRequestedStatus.None)
            val allResult = allDeferred.await()
            val pendingResult = pendingDeferred.await()
            val completedResult = completedDeferred.await()

            _uiState.update { state ->
                state.copy(
                    isInitialLoading = false,
                    requestedStatus = requestedStatus,
                    allRequests = allResult,
                    pendingRequests = pendingResult,
                    completedRequests = completedResult
                )
            }
        }
    }

    private suspend fun loadFirstPage(
        filter: VerdictRequestFilter
    ): PaginatedList<VerdictRequest> {
        return getVerdictRequestsUseCase(
            filter = filter,
            page = FIRST_PAGE,
            size = PAGE_SIZE
        ).fold(
            onSuccess = { response ->
                PaginatedList(
                    items = response.items,
                    hasMore = response.hasMore,
                    currentPage = FIRST_PAGE,
                    totalCount = response.totalCount,
                    isLoading = false
                )
            },
            onFailure = {
                PaginatedList(error = it.message)
            }
        )
    }

    fun loadMore() {
        val currentState = _uiState.value
        val currentList = currentState.currentList

        if (!currentList.canLoadMore) return

        val currentFilter = currentState.selectedFilter

        viewModelScope.launch {
            updatedRequestsOf(currentFilter) {
                it.copy(isLoading = true, error = null)
            }

            val nextPage = currentList.currentPage + 1

            getVerdictRequestsUseCase(
                filter = currentState.selectedFilter,
                page = nextPage,
                size = PAGE_SIZE
            ).fold(
                onSuccess = { result ->
                    updatedRequestsOf(currentFilter) { current ->
                        current.copy(
                            items = current.items + result.items,
                            hasMore = result.hasMore,
                            currentPage = nextPage,
                            isLoading = false,
                            totalCount = current.totalCount,
                            error = null
                        )
                    }
                },
                onFailure = { error ->
                    updatedRequestsOf(currentFilter) { current ->
                        current.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }

    private fun updatedRequestsOf(
        filter: VerdictRequestFilter,
        update: (PaginatedList<VerdictRequest>) -> PaginatedList<VerdictRequest>
    ) {
        _uiState.update { state ->
            when (filter) {
                VerdictRequestFilter.All -> state.copy(allRequests = update(state.allRequests))

                VerdictRequestFilter.Pending -> state.copy(pendingRequests = update(state.pendingRequests))

                VerdictRequestFilter.Completed -> state.copy(completedRequests = update(state.completedRequests))
            }
        }
    }

    fun refresh() {
        val currentFilter = _uiState.value.selectedFilter

        viewModelScope.launch {
            updatedRequestsOf(currentFilter) {
                it.copy(isLoading = true, error = null)
            }

            val result = loadFirstPage(currentFilter)

            updatedRequestsOf(currentFilter) { result }
        }
    }

    fun selectFilter(filter: VerdictRequestFilter) {
        if (_uiState.value.selectedFilter == filter) return
        _uiState.update { state -> state.copy(selectedFilter = filter) }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val FIRST_PAGE = 0
    }
}