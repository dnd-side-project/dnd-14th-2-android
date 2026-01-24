package com.smtm.pickle.presentation.createledger.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.model.ledger.PaymentType

private const val MEMO_MAX_LENGTH = 100

@Composable
fun Step2Content(
    modifier: Modifier = Modifier,
    selectedPaymentType: PaymentType?,
    memo: String,
    canSubmit: Boolean,
    isLoading: Boolean,
    onPaymentTypeSelect: (PaymentType) -> Unit,
    onMemoChange: (String) -> Unit,
    onPreviousClick: () -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "결제 유형을 선택해주세요.*",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PaymentType.entries.forEach { paymentType ->
                FilterChip(
                    selected = selectedPaymentType == paymentType,
                    onClick = { onPaymentTypeSelect(paymentType) },
                    label = { Text(paymentType.label) }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "메모할 내용이 있나요?",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = memo,
            onValueChange = {
                if (it.length <= MEMO_MAX_LENGTH) {
                    onMemoChange(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            placeholder = { Text("메모를 입력해주세요...") },
            supportingText = {
                Text(
                    text = "${memo.length}/$MEMO_MAX_LENGTH",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onPreviousClick,
                modifier = Modifier.weight(1f),
                enabled = !isLoading,
            ) {
                Text("이전")
            }

            Button(
                onClick = onSubmitClick,
                modifier = Modifier.weight(1f),
                enabled = canSubmit && !isLoading,
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("입력 완료")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}