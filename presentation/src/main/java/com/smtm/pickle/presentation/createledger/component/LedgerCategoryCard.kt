package com.smtm.pickle.presentation.createledger.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.model.ledger.Category

@Composable
fun LedgerCategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.Black.copy(alpha = 0.8f)
            else Color.Gray.copy(alpha = 0.15f),
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(if (isSelected) Color.Green else Color.Gray)
            )

            Spacer(modifier.height(6.dp))

            Text(
                text = category.label,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

@Preview("selected")
@Composable
private fun LedgerCategoryCardSelectedPreview() {
    MaterialTheme {
        LedgerCategoryCard(
            modifier = Modifier.width(width = 200.dp),
            category = Category.EDUCATION,
            isSelected = true,
            onClick = {}
        )
    }
}

@Preview("unselected")
@Composable
private fun LedgerCategoryCardUnSelectedPreview() {
    MaterialTheme {
        LedgerCategoryCard(
            modifier = Modifier.width(width = 200.dp),
            category = Category.EDUCATION,
            isSelected = false,
            onClick = {}
        )
    }
}