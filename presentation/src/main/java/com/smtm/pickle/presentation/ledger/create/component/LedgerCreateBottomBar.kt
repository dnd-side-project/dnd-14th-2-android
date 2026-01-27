package com.smtm.pickle.presentation.ledger.create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.button.PickleButton
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.ledger.create.LedgerCreateStep

@Composable
fun LedgerCreateBottomBar(
    modifier: Modifier = Modifier,
    step: LedgerCreateStep,
    enableNext: Boolean,
    enabledSuccess: Boolean,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    when (step) {
        LedgerCreateStep.FIRST -> {
            LedgerCreateFirstBottomButton(
                modifier = modifier,
                enableNext = enableNext,
                onNextClick = onNextClick
            )
        }

        LedgerCreateStep.SECOND -> {
            LedgerCreateSecondBottomButtons(
                modifier = modifier,
                enabledSuccess = enabledSuccess,
                onPreviousClick = onPreviousClick,
                onSuccessClick = onSuccessClick
            )
        }
    }
}

@Composable
private fun LedgerCreateFirstBottomButton(
    modifier: Modifier = Modifier,
    enableNext: Boolean,
    onNextClick: () -> Unit
) {
    PickleButton(
        modifier = modifier,
        text = stringResource(R.string.common_next),
        onClick = onNextClick,
        enabled = enableNext,
        color = if (enableNext) PickleTheme.colors.primary400 else PickleTheme.colors.gray100,
        textColor = if (enableNext) PickleTheme.colors.base0 else PickleTheme.colors.gray600,
    )
}

@Composable
private fun LedgerCreateSecondBottomButtons(
    modifier: Modifier = Modifier,
    enabledSuccess: Boolean,
    onPreviousClick: () -> Unit,
    onSuccessClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PickleButton(
            modifier = Modifier.width(96.dp),
            text = stringResource(R.string.common_previous),
            color = PickleTheme.colors.gray100,
            textColor = PickleTheme.colors.gray600,
            onClick = onPreviousClick,
        )

        PickleButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.common_input_success),
            color = if (enabledSuccess) PickleTheme.colors.primary400 else PickleTheme.colors.gray100,
            textColor = if (enabledSuccess) PickleTheme.colors.base0 else PickleTheme.colors.gray600,
            onClick = onSuccessClick,
            enabled = enabledSuccess,
        )
    }
}

@Preview(
    name = "LedgerCreateFirstBottomButtonPreview - Disable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstBottomButtonDisablePreview() {
    PickleTheme {
        LedgerCreateFirstBottomButton(
            enableNext = false,
            onNextClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateFirstBottomButtonPreview - Enable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateFirstBottomButtonEnablePreview() {
    PickleTheme {
        LedgerCreateFirstBottomButton(
            enableNext = true,
            onNextClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Disable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsDisablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = false,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}

@Preview(
    name = "LedgerCreateSecondBottomButtonsPreview - Enable",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun LedgerCreateSecondBottomButtonsEnablePreview() {
    PickleTheme {
        LedgerCreateSecondBottomButtons(
            enabledSuccess = true,
            onPreviousClick = {},
            onSuccessClick = {}
        )
    }
}