package com.smtm.pickle.presentation.designsystem.components.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.components.PickleSupportingText
import com.smtm.pickle.presentation.designsystem.components.textfield.model.InputState
import com.smtm.pickle.presentation.designsystem.components.textfield.model.KeyboardLocale
import com.smtm.pickle.presentation.designsystem.components.textfield.model.TextFieldInteraction
import com.smtm.pickle.presentation.designsystem.components.textfield.model.toUiState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * 기본 디자인이 적용된 텍스트 필드
 * @param modifier
 * @param value 텍스트 필드 입력 값
 * @param onValueChange 텍스트 필드 입력 값 변경
 * @param height 텍스트 필드 높이
 * @param enabled 텍스트 필드 활성화 여부
 * @param readOnly 텍스트 필드 읽기 전용 여부
 * @param hint 텍스트 필드 힌트
 * @param leadingIcon 텍스트 필드 앞쪽 아이콘
 * @param trailingIcon 텍스트 필드 뒤쪽 아이콘
 * @param isError 호출 부의 에러 조건
 * @param singleLine 단일 라인 입력 여부
 * @param keyboardType 키보드 타입(텍스트, 이메일 등)
 * @param imeAction 키보드 액션 버튼
 * @param locale 소프트 키보드 노출 시 언어
 */
@Composable
fun PickleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    height: Dp = Dimensions.inputHeight,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    hint: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    interaction: TextFieldInteraction = TextFieldInteraction.DEFAULT,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    locale: KeyboardLocale = KeyboardLocale.KOREA,
) {
    val isErrorValue = isError && value.isNotEmpty()
    val focusedBorderColor = when (interaction) {
        TextFieldInteraction.DEFAULT ->
            PickleTheme.colors.primary400

        TextFieldInteraction.ONLY_ERROR_INTERACTION, TextFieldInteraction.NO_INTERACTION ->
            PickleTheme.colors.transparent
    }

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = PickleTheme.typography.body4Medium.copy(
            color = PickleTheme.colors.gray800,
        ),
        placeholder = {
            Text(
                text = hint,
                style = PickleTheme.typography.body4Medium,
                color = PickleTheme.colors.gray500,
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isErrorValue,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction,
            hintLocales = LocaleList(
                Locale(locale.language)
            )
        ),
        keyboardActions = KeyboardActions(),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else 5,
        shape = RoundedCornerShape(Dimensions.radius),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = PickleTheme.colors.gray50,
            unfocusedContainerColor = PickleTheme.colors.gray50,
            errorContainerColor = PickleTheme.colors.gray50,

            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = PickleTheme.colors.transparent,
            errorBorderColor = PickleTheme.colors.error50,

            unfocusedSupportingTextColor = PickleTheme.colors.gray600,
            errorSupportingTextColor = PickleTheme.colors.error50,
        ),
    )
}

@Composable
fun PickleTextFieldWithSupporting(
    value: String,
    inputState: InputState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    defaultSupportingText: String? = null,
) {
    val (isError, supportingText) = inputState.toUiState(defaultSupportingText)

    Column(modifier = modifier.fillMaxWidth()) {
        PickleTextField.Default(
            value = value,
            onValueChange = onValueChange,
            hint = hint,
            trailingIcon = trailingIcon,
            isError = isError,
        )

        supportingText?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                PickleSupportingText(
                    message = it,
                    inputState = inputState,
                )
            }
        }
    }
}

object PickleTextField {

    @Composable
    fun Default(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        hint: String = "",
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        isError: Boolean = false,
        interaction: TextFieldInteraction = TextFieldInteraction.DEFAULT,
        keyboardType: KeyboardType = KeyboardType.Text,
        imeAction: ImeAction = ImeAction.Done,
        locale: KeyboardLocale = KeyboardLocale.KOREA
    ) {
        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            hint = hint,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            interaction = interaction,
            keyboardType = keyboardType,
            imeAction = imeAction,
            locale = locale,
        )
    }

    @Composable
    fun MultiLine(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        readOnly: Boolean = false,
        hint: String = "",
        imeAction: ImeAction = ImeAction.Done,
        locale: KeyboardLocale = KeyboardLocale.KOREA
    ) {
        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            hint = hint,
            singleLine = false,
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
            locale = locale,
        )
    }

    @Composable
    fun Search(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        height: Dp = Dimensions.searchHeight,
        hint: String = "",
    ) {
        PickleTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            height = height,
            hint = hint,
            leadingIcon = { /* TODO: 검색 아이콘 추가 */ },
//            trailingIcon = if (value.isNotEmpty()) /* TODO: 삭제 아이콘 추가 */ else null,
            isError = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        )
    }
}

@Preview
@Composable
private fun EmptyTextField() {
    PickleTextField(
        value = "",
        onValueChange = {}
    )
}

@Preview
@Composable
private fun TextFieldWithHint() {
    PickleTextField(
        value = "",
        onValueChange = {},
        hint = "텍스트를 입력해주세요",
    )
}

@Preview
@Composable
private fun TextFieldWithValue() {
    PickleTextField(
        value = "가나다라마바사",
        onValueChange = {},
        hint = "텍스트를 입력해주세요",
    )
}

@Preview
@Composable
private fun TextFieldWithSupporting() {
    PickleTextFieldWithSupporting(
        value = "가나다라마바사",
        onValueChange = {},
        defaultSupportingText = "텍스트를 입력해주세요",
        inputState = InputState.Idle,
    )
}
