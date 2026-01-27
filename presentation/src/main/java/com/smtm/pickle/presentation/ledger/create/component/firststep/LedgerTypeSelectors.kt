package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.home.model.LedgerTypeUi

@Composable
fun LedgerTypeSelectors(
    modifier: Modifier = Modifier,
    onLedgerTypeClick: (LedgerTypeUi) -> Unit,
    selectedType: LedgerTypeUi? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LedgerTypeChip(
            modifier = modifier.weight(1f),
            isSelected = LedgerTypeUi.Income == selectedType,
            type = LedgerTypeUi.Income,
            onClick = {
                onLedgerTypeClick(LedgerTypeUi.Income)
            }
        )

        LedgerTypeChip(
            modifier = modifier.weight(1f),
            isSelected = LedgerTypeUi.Expense == selectedType,
            type = LedgerTypeUi.Expense,
            onClick = {
                onLedgerTypeClick(LedgerTypeUi.Expense)
            }
        )
    }
}

@Composable
private fun LedgerTypeChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    type: LedgerTypeUi,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) PickleTheme.colors.gray700 else PickleTheme.colors.gray100
    val textColor = if (isSelected) PickleTheme.colors.base0 else PickleTheme.colors.gray600

    Surface(
        modifier = modifier.height(60.dp),
        onClick = onClick,
        color = backgroundColor,
        shape = RoundedCornerShape(Dimensions.radius)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(type.stringResId),
                color = textColor,
                style = PickleTheme.typography.body1Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    name = "LedgerTypeSelectors - None Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerTypeSelectorsNoneSelectedPreview() {
    PickleTheme {
        LedgerTypeSelectors(
            onLedgerTypeClick = {},
            selectedType = null
        )
    }
}

@Preview(
    name = "LedgerTypeSelectors - Income Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerTypeSelectorsIncomeSelectedPreview() {
    PickleTheme {
        LedgerTypeSelectors(
            onLedgerTypeClick = {},
            selectedType = LedgerTypeUi.Income
        )
    }
}

@Preview(
    name = "LedgerTypeSelectors - Expense Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerTypeSelectorsExpenseSelectedPreview() {
    PickleTheme {
        LedgerTypeSelectors(
            onLedgerTypeClick = {},
            selectedType = LedgerTypeUi.Expense
        )
    }
}

@Preview(
    name = "LedgerTypeChip - Selected",
    showBackground = true
)
@Composable
private fun LedgerTypeChipSelectedPreview() {
    PickleTheme {
        LedgerTypeChip(
            modifier = Modifier.width(160.dp),
            isSelected = true,
            type = LedgerTypeUi.Income,
            onClick = {}
        )
    }
}

@Preview(
    name = "LedgerTypeChip - Unselected",
    showBackground = true
)
@Composable
private fun LedgerTypeChipUnselectedPreview() {
    PickleTheme {
        LedgerTypeChip(
            modifier = Modifier.width(160.dp),
            isSelected = false,
            type = LedgerTypeUi.Expense,
            onClick = {}
        )
    }
}