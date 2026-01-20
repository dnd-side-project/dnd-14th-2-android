package com.smtm.pickle.presentation.common

data class PaginatedList<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val hasMore: Boolean = true,
    val currentPage: Int = -1,
    val error: String? = null,
) {
    val isInitialized: Boolean
        get() = currentPage >= 0

    val canLoadMore: Boolean
        get() = !isLoading && hasMore && error == null
}
