package com.smtm.pickle.presentation.designsystem.components.picklenavhost

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableFab(
    expendedText: String,
    expandedActionImageVector: ImageVector,
    collapsedActionImageVector: ImageVector,
    isExpanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    onActionClick: () -> Unit,
) {
    if (isExpanded) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = expendedText,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(onClick = onActionClick) {
                Icon(expandedActionImageVector, contentDescription = null)
            }
        }
    } else {
        FloatingActionButton(onClick = { onExpandChanged(true) }) {
            Icon(collapsedActionImageVector, contentDescription = null)
        }
    }
}