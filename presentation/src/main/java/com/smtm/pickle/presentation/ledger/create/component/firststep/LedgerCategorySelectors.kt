package com.smtm.pickle.presentation.ledger.create.component.firststep

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions
import com.smtm.pickle.presentation.home.model.CategoryUi
import com.smtm.pickle.presentation.ledger.create.component.LedgerCreateHeaderText

@Composable
fun LedgerCategorySelectors(
    modifier: Modifier = Modifier,
    selectedCategory: CategoryUi? = null,
    onCategoryClick: (CategoryUi) -> Unit
) {
    Column(modifier = modifier) {
        LedgerCreateHeaderText(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            text = stringResource(R.string.ledger_create_category_header),
            highlightText = stringResource(R.string.ledger_create_category_header_highlight),
        )

        CategoryGrid(
            modifier = Modifier.padding(horizontal = 16.dp),
            selectedCategory = selectedCategory,
            onCategoryClick = onCategoryClick,
        )
    }
}

@Composable
private fun CategoryGrid(
    modifier: Modifier = Modifier,
    selectedCategory: CategoryUi? = null,
    onCategoryClick: (CategoryUi) -> Unit,
) {
    val categories = listOf(
        CategoryUi.Food, CategoryUi.Transport, CategoryUi.Housing,
        CategoryUi.Shopping, CategoryUi.HealthMedical, CategoryUi.EducationSelfDevelopment,
        CategoryUi.LeisureHobby, CategoryUi.SavingFinance, CategoryUi.Other
    )
    val rows = categories.chunked(3)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rowItems.forEach { category ->
                    CategoryChip(
                        modifier = Modifier.weight(1f),
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { onCategoryClick(category) }
                    )
                }

            }
        }
    }
}

@Composable
private fun CategoryChip(
    modifier: Modifier = Modifier,
    category: CategoryUi,
    isSelected: Boolean,
    onClick: (CategoryUi) -> Unit
) {
    val backgroundColor = if (isSelected) PickleTheme.colors.gray700 else PickleTheme.colors.gray50
    val textColor = if (isSelected) PickleTheme.colors.base0 else PickleTheme.colors.gray700
    val imageResId = if (isSelected) category.activatedIconResId else category.inactivatedIconResId

    Surface(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(Dimensions.radius),
        color = backgroundColor,
        onClick = { onClick(category) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = "category_icon",
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(category.stringResId),
                style = PickleTheme.typography.body1Bold,
                color = textColor
            )
        }

    }
}

@Preview(
    name = "LedgerCategorySelectorsPreview - Non Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCategorySelectorsNonSelectedPreview() {
    PickleTheme {
        LedgerCategorySelectors(
            selectedCategory = null,
            onCategoryClick = {}
        )
    }
}

@Preview(
    name = "LedgerCategorySelectorsPreview - Non Selected",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCategorySelectorsSelectedPreview() {
    PickleTheme {
        LedgerCategorySelectors(
            selectedCategory = CategoryUi.Housing,
            onCategoryClick = {}
        )
    }
}