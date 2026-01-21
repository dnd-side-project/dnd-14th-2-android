package com.smtm.pickle.presentation.verdict.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.smtm.pickle.domain.model.verdict.PaymentMethod
import com.smtm.pickle.domain.model.verdict.VerdictRequest
import com.smtm.pickle.domain.model.verdict.VerdictStatus
import com.smtm.pickle.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerdictRequestInfoBottomSheet(
    sheetState: SheetState,
    request: VerdictRequest,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Header(status = request.status)
            Spacer(modifier = Modifier.height(26.dp))
            Participants(request = request)
            Spacer(modifier = Modifier.height(20.dp))
            SpendingInfo(
                title = request.spendingTitle,
                category = request.spendingCategory,
                paymentMethod = request.paymentMethod,
            )
            Spacer(modifier = Modifier.height(20.dp))
            request.spendingReview?.let { review ->
                SpendingReview(review = review)
            }
        }
    }
}

@Composable
private fun Header(status: VerdictStatus) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "진행중인 심판",
            style = MaterialTheme.typography.titleLarge
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = when (status) {
                VerdictStatus.Pending -> MaterialTheme.colorScheme.primaryContainer
                VerdictStatus.Completed -> MaterialTheme.colorScheme.secondaryContainer
            }
        ) {
            Text(
                text = when (status) {
                    VerdictStatus.Pending -> stringResource(R.string.verdict_filter_pending)
                    VerdictStatus.Completed -> stringResource(R.string.verdict_filter_completed)
                },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Composable
private fun Participants(request: VerdictRequest) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ParticipantInfo(
            label = "신청인",
            nickname = request.myNickname,
        )
        ParticipantInfo(
            label = "배심원",
            nickname = request.jurorNickname
        )
    }
}

@Composable
private fun ParticipantInfo(
    label: String,
    nickname: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = nickname,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun SpendingInfo(
    title: String,
    category: String,
    paymentMethod: PaymentMethod,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = when (paymentMethod) {
                    PaymentMethod.Cash -> "$category · 현금"
                    PaymentMethod.Card -> "$category · 카드"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SpendingReview(review: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp),
        text = review,
        style = MaterialTheme.typography.bodyLarge,
    )
}