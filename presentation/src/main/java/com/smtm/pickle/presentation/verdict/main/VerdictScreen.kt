package com.smtm.pickle.presentation.verdict.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.utils.emptyWindowInsets
import com.smtm.pickle.presentation.navigation.navigator.VerdictNavigator

private const val VERDICT_REQUEST_ITEM_KEY_PREFIX = "verdict"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerdictScreen(
    navigator: VerdictNavigator,
    viewModel: VerdictViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(
        contentWindowInsets = emptyWindowInsets,
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = emptyWindowInsets,
                title = { Text(stringResource(R.string.verdict_title)) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Groups, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        VerdictContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            listState = listState,
            onFilterClick = viewModel::selectFilter,
            onLoadMore = viewModel::loadMore,
            onRequestItemClick = {}
        )
    }
}

@Composable
private fun VerdictContent(
    modifier: Modifier = Modifier,
    uiState: VerdictUiState,
    listState: LazyListState,
    onFilterClick: (VerdictRequestFilter) -> Unit,
    onLoadMore: () -> Unit,
    onRequestItemClick: (VerdictRequest) -> Unit
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull {
                it.key.toString().startsWith(VERDICT_REQUEST_ITEM_KEY_PREFIX)
            }
            val totalItems = layoutInfo.totalItemsCount

            // 마지막 3번째 아이템이 보이면 추가 로드
            lastVisibleItem != null && totalItems > 0 && lastVisibleItem.index >= totalItems - 3
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && uiState.currentList.canLoadMore) onLoadMore()
    }

    if (uiState.isInitialLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        bannerSection(
            requestedStatus = uiState.requestedStatus,
            onMoreClick = {}
        )
        requestsHeaderSection(
            selectedFilter = uiState.selectedFilter,
            totalCount = uiState.currentList.totalCount,
            onFilterTabClick = onFilterClick
        )
        requestListSection(
            items = uiState.currentList.items,
            isLoading = uiState.currentList.isLoading,
            error = uiState.currentList.error,
            onItemClick = onRequestItemClick,
            onOptionClick = { }
        )
    }
}

private fun LazyListScope.bannerSection(
    requestedStatus: VerdictRequestedStatus,
    onMoreClick: () -> Unit,
) {
    item(key = "banner") {
        VerdictRequestedBanner(
            status = requestedStatus,
            onClick = onMoreClick
        )
    }
    item(key = "divider") {
        HorizontalDivider(thickness = 10.dp)
    }
}

@Composable
private fun VerdictRequestedBanner(
    modifier: Modifier = Modifier,
    status: VerdictRequestedStatus,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Default.Pending,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        when (status) {
            VerdictRequestedStatus.None -> {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = stringResource(R.string.verdict_banner_no_request))
                    Text(text = stringResource(R.string.verdict_banner_will_notify))
                }
            }

            VerdictRequestedStatus.Requested -> {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.verdict_banner_request_arrived))
                        Spacer(modifier = Modifier.width(6.dp))
                        Image(
                            imageVector = Icons.Default.NewReleases,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(text = stringResource(R.string.verdict_banner_check_spending))
                }
                TextButton(
                    modifier = Modifier.padding(6.dp),
                    shape = ShapeDefaults.Medium,
                    onClick = onClick
                ) {
                    Text(text = stringResource(R.string.verdict_banner_view))
                }
            }
        }
    }
}

private fun LazyListScope.requestsHeaderSection(
    selectedFilter: VerdictRequestFilter,
    totalCount: Int,
    onFilterTabClick: (VerdictRequestFilter) -> Unit
) {
    item(key = "requests_header") {
        VerdictRequestsHeader(
            selectedFilter = selectedFilter,
            totalCount = totalCount,
            onFilterTabClick = onFilterTabClick
        )
    }
}

@Composable
private fun VerdictRequestsHeader(
    selectedFilter: VerdictRequestFilter,
    totalCount: Int,
    onFilterTabClick: (VerdictRequestFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.verdict_my_requests),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        VerdictRequestTabs(selectedFilter = selectedFilter, onFilterClick = onFilterTabClick)
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(R.string.verdict_total_count, totalCount)
        )
    }
}

@Composable
private fun VerdictRequestTabs(
    selectedFilter: VerdictRequestFilter,
    onFilterClick: (VerdictRequestFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = selectedFilter == VerdictRequestFilter.All,
            onClick = { onFilterClick(VerdictRequestFilter.All) },
            label = { Text(stringResource(R.string.verdict_filter_all)) }
        )
        FilterChip(
            selected = selectedFilter == VerdictRequestFilter.Pending,
            onClick = { onFilterClick(VerdictRequestFilter.Pending) },
            label = { Text(stringResource(R.string.verdict_filter_pending)) }
        )
        FilterChip(
            selected = selectedFilter == VerdictRequestFilter.Completed,
            onClick = { onFilterClick(VerdictRequestFilter.Completed) },
            label = { Text(stringResource(R.string.verdict_filter_completed)) }
        )

    }
}

private fun LazyListScope.requestListSection(
    items: List<VerdictRequest>,
    isLoading: Boolean,
    error: String?,
    onItemClick: (VerdictRequest) -> Unit,
    onOptionClick: (VerdictRequest) -> Unit,
) {
    val isEmpty = items.isEmpty() && !isLoading && error == null
    if (isEmpty) {
        item(key = "empty") {
            VerdictEmptyItem()
        }
    } else {
        items(
            items = items,
            key = { "${VERDICT_REQUEST_ITEM_KEY_PREFIX}_${it.id}" }
        ) { item ->
            VerdictItem(
                item = item,
                onItemClick = onItemClick,
                onOptionClick = onOptionClick
            )
        }

        if (isLoading) {
            item("paging_loading") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
private fun VerdictEmptyItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Pending,
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.verdict_empty_result),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun VerdictItem(
    modifier: Modifier = Modifier,
    item: VerdictRequest,
    onItemClick: (VerdictRequest) -> Unit = {},
    onOptionClick: (VerdictRequest) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) }),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = item.nickname)

                Spacer(modifier = Modifier.width(6.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.badge)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (item.status) {
                        VerdictStatus.Pending -> stringResource(R.string.verdict_filter_pending)
                        VerdictStatus.Completed -> stringResource(R.string.verdict_filter_completed)
                    }
                )
                Spacer(modifier = Modifier.width(3.dp))
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(Color.Gray),
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(stringResource(R.string.verdict_joined_count, item.joinedCount))
            }
        }

        IconButton(onClick = { onOptionClick(item) }) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}