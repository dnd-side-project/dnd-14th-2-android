package com.smtm.pickle.presentation.createledger.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.model.ledger.Category
import com.smtm.pickle.presentation.model.ledger.LedgerType

@Composable
fun Step1Content(
    modifier: Modifier = Modifier,
    amount: String,
    ledgerType: LedgerType,
    selectedCategory: Category?,
    title: String,
    canProceed: Boolean,
    onAmountChange: (String) -> Unit,
    onLedgerTypeChange: (LedgerType) -> Unit,
    onCategorySelect: (Category) -> Unit,
    onTitleChange: (String) -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        MoneyInputField(
            modifier = Modifier.fillMaxWidth(),
            rawDigits = amount,
            onRawDigitsChange = onAmountChange,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LedgerType.entries.forEach { type ->
                val isSelected = ledgerType == type
                if (isSelected) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onLedgerTypeChange(type) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (type == LedgerType.INCOME) {
                                Color(0xFF4CAF50)
                            } else {
                                Color(0xFF2196F3)
                            }
                        ),
                    ) {
                        Text(text = type.label)
                    }
                } else {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onLedgerTypeChange(type) },
                    ) {
                        Text(text = type.label)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "카테고리를 선택해주세요.*",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        LedgerCategoryCardGrid(
            modifier = Modifier.fillMaxWidth(),
            items = Category.entries.toList(),
            selectedCategory = selectedCategory,
            onItemClick = onCategorySelect
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 가계부 내용 입력
        Text(
            text = "가계부 내용을 입력해주세요.",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("소고기 삼겹살") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 다음 버튼
        Button(
            onClick = onNextClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = canProceed,
        ) {
            Text("다음")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun Step1ContentPreview() {
    MaterialTheme {
        Surface {
            Step1Content(
                amount = "10000",
                ledgerType = LedgerType.INCOME,
                selectedCategory = Category.FOOD,
                title = "소고기 삼겹살",
                canProceed = true,
                onAmountChange = {},
                onLedgerTypeChange = {},
                onCategorySelect = {},
                onTitleChange = {},
                onNextClick = {}
            )
        }
    }
}