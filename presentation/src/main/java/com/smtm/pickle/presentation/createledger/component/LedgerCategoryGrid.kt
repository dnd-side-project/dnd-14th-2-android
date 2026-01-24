package com.smtm.pickle.presentation.createledger.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.model.ledger.Category

@Composable
fun LedgerCategoryCardGrid(
    modifier: Modifier = Modifier,
    items: List<Category>,
    selectedCategory: Category? = null,
    onItemClick: (Category) -> Unit,
) {
    val rows = items.chunked(3)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                rowItems.forEach { item ->
                    LedgerCategoryCard(
                        modifier = Modifier.weight(1f),
                        category = item,
                        isSelected = item == selectedCategory,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LedgerCategoryCardGridPreview() {
    MaterialTheme {
        LedgerCategoryCardGrid(
            items = Category.entries.toList(),
            onItemClick = {}
        )
    }
}