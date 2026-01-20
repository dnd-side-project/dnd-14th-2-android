package com.smtm.pickle.domain.model.common

data class PaginatedResult<T>(
    val items: List<T>,
    val hasMore: Boolean,
    val totalCount: Int = 0
)
