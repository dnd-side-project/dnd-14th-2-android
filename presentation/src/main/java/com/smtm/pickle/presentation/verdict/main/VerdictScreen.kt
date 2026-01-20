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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictRequestFilter
import com.smtm.pickle.domain.model.verdict.VerdictRequestedStatus
import com.smtm.pickle.domain.model.verdict.VerdictStatus
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
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("심판") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Groups, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        VerdictContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            listState = listState,
            onFilterSelect = viewModel::selectFilter,
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
    onFilterSelect: (VerdictRequestFilter) -> Unit,
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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item(key = "banner") {
            VerdictRequestedBanner(
                status = uiState.requestedStatus,
                onClick = {}
            )
        }
        item(key = "divider") {
            HorizontalDivider(thickness = 10.dp)
        }
        item("header") {
            VerdictRequestsHeader(
                selectedTab = uiState.selectedFilter,
                totalCounts = 10,
                onSelectTabClick = onFilterSelect
            )
        }
        items(
            items = uiState.currentList.items,
            key = { "${VERDICT_REQUEST_ITEM_KEY_PREFIX}_${it.id}" }
        ) { item ->
            VerdictItem(
                item = item,
                onItemClick = onRequestItemClick,
                onOptionClick = {},
            )
        }

        // 로딩 인디케이터
        if (uiState.currentList.isLoading) {
            item(key = "loading") {
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
        // 에러 표시
        uiState.currentList.error?.let { error ->
            item(key = "error") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onLoadMore) {
                        Text("다시 시도")
                    }
                }
            }
        }

        // 더 이상 데이터 없음
        if (!uiState.currentList.hasMore && uiState.currentList.items.isNotEmpty()) {
            item(key = "end") {
                Text(
                    text = "모든 요청을 불러왔습니다",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
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
                    Text(text = "새로운 심판 요청이 없어요")
                    Text(text = "요청이 오면 알려드릴게요")
                }
            }

            VerdictRequestedStatus.Requested -> {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "심판 요청이 도착했어요")
                        Spacer(modifier = Modifier.width(6.dp))
                        Image(
                            imageVector = Icons.Default.NewReleases,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(text = "소비를 확인하고 판결해보세요")
                }
                TextButton(
                    modifier = Modifier.padding(6.dp),
                    shape = ShapeDefaults.Medium,
                    onClick = onClick
                ) {
                    Text(text = "보기")
                }
            }
        }
    }
}

@Composable
private fun VerdictRequestsHeader(
    selectedTab: VerdictRequestFilter,
    totalCounts: Int,
    onSelectTabClick: (VerdictRequestFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "내가 보낸 심판요청",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        VerdictRequestTabs(selectedTab = selectedTab, onSelect = onSelectTabClick)
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = "전체 $totalCounts"
        )
    }
}

@Composable
private fun VerdictRequestTabs(
    selectedTab: VerdictRequestFilter,
    onSelect: (VerdictRequestFilter) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = selectedTab == VerdictRequestFilter.All,
            onClick = { onSelect(VerdictRequestFilter.All) },
            label = { Text("전체") }
        )
        FilterChip(
            selected = selectedTab == VerdictRequestFilter.Pending,
            onClick = { onSelect(VerdictRequestFilter.Pending) },
            label = { Text("대기중") }
        )
        FilterChip(
            selected = selectedTab == VerdictRequestFilter.Completed,
            onClick = { onSelect(VerdictRequestFilter.Completed) },
            label = { Text("완료") }
        )

    }
}

@Composable
fun VerdictItem(
    modifier: Modifier = Modifier,
    item: VerdictRequest,
    onItemClick: (VerdictRequest) -> Unit = {},
    onOptionClick: (VerdictRequest) -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = { onItemClick(item) }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = modifier.width(12.dp))

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier,
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
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (item.status) {
                        VerdictStatus.Pending -> "대기중"
                        VerdictStatus.Completed -> "완료"
                    }
                )
                Spacer(modifier = Modifier.width(3.dp))
                Box(
                    modifier = Modifier
                        .size(3.dp)
                        .background(Color.Gray),
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text("함께한 심판 ${item.joinedCount}회")
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