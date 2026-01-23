package com.smtm.pickle.presentation.designsystem.components.tooltip

import android.R.attr.textColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.smtm.pickle.presentation.designsystem.components.tooltip.model.TailPosition
import com.smtm.pickle.presentation.designsystem.components.tooltip.model.TailSize
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * Pickle 말풍선 (툴팁)
 *
 * @param message 말풍선에 표시할 메시지
 * @param modifier Modifier
 * @param tailPosition 말풍선의 꼬리 위치
 * @param isVisible 말풍선의 표시 여부
 */
@Composable
fun PickleTooltip(
    message: String,
    subText: String? = null,
    modifier: Modifier = Modifier,
    tailPosition: TailPosition = TailPosition.BOTTOM,
    isVisible: Boolean = false,
) {
    val backgroundColor = PickleTheme.colors.gray700

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(200)) +
                scaleIn(initialScale = 0.95f, animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(150)) +
                scaleOut(targetScale = 0.95f, animationSpec = tween(150))
    ) {
        when (tailPosition) {
            TailPosition.TOP -> {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = TailSize.Vertical.width,
                                height = TailSize.Vertical.height
                            )
                            .drawBehind {
                                drawTopTail(backgroundColor)
                            }
                    )
                    TooltipBody(
                        message = message,
                        subText = subText
                    )
                }
            }

            TailPosition.BOTTOM -> {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TooltipBody(
                        message = message,
                        subText = subText
                    )

                    Box(
                        modifier = Modifier
                            .size(
                                width = TailSize.Vertical.width,
                                height = TailSize.Vertical.height
                            )
                            .drawBehind {
                                drawBottomTail(backgroundColor)
                            }
                    )
                }
            }

            TailPosition.LEFT -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = TailSize.Horizontal.width,
                                height = TailSize.Horizontal.height
                            )
                            .drawBehind {
                                drawLeftTail(backgroundColor)
                            }
                    )
                    TooltipBody(
                        message = message,
                        subText = subText
                    )
                }
            }

            TailPosition.RIGHT -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TooltipBody(
                        message = message,
                        subText = subText
                    )

                    Box(
                        modifier = Modifier
                            .size(
                                width = TailSize.Horizontal.width,
                                height = TailSize.Horizontal.height
                            )
                            .drawBehind {
                                drawRightTail(backgroundColor)
                            }
                    )
                }
            }

            TailPosition.LEFT_TOP -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = TailSize.Horizontal.width,
                                height = TailSize.Horizontal.height
                            )
                            .drawBehind {
                                drawLeftTail(backgroundColor)
                            }
                    )
                    TooltipBody(
                        message = message,
                        subText = subText
                    )
                }
            }

            TailPosition.RIGHT_TOP -> {
                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.Top
                ) {
                    TooltipBody(
                        message = message,
                        subText = subText
                    )

                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .size(
                                    width = TailSize.Horizontal.width,
                                    height = TailSize.Horizontal.height
                                )
                                .drawBehind {
                                    drawRightTail(backgroundColor)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TooltipBody(
    message: String,
    subText: String? = null
) {
    Surface(
        shape = RoundedCornerShape(Dimensions.radiusSmall),
        color = PickleTheme.colors.gray700,
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f, fill = false)) {
                Text(
                    text = message,
                    style = PickleTheme.typography.body2Medium,
                    color = PickleTheme.colors.base0
                )

                subText?.let {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = subText,
                        style = PickleTheme.typography.caption1Medium,
                        color = PickleTheme.colors.base0.copy(alpha = 0.64f)
                    )
                }
            }
        }
    }
}

/** 위쪽 꼬리 */
private fun DrawScope.drawTopTail(color: Color) {
    val path = Path().apply {
        moveTo(size.width / 2, 0f)      // 위 중앙 꼭지점
        lineTo(0f, size.height)         // 왼쪽 아래
        lineTo(size.width, size.height) // 오른쪽 아래
        close()
    }
    drawPath(path, color)
}

/** 아래쪽 꼬리 */
private fun DrawScope.drawBottomTail(color: Color) {
    val path = Path().apply {
        moveTo(0f, 0f)                      // 왼쪽 위
        lineTo(size.width, 0f)              // 오른쪽 위
        lineTo(size.width / 2, size.height) // 아래 중앙 꼭지점
        close()
    }
    drawPath(path, color)
}

/** 왼쪽 꼬리 */
private fun DrawScope.drawLeftTail(color: Color) {
    val path = Path().apply {
        moveTo(0f, size.height / 2)     // 왼쪽 중앙 꼭지점
        lineTo(size.width, 0f)          // 오른쪽 위
        lineTo(size.width, size.height) // 오른쪽 아래
        close()
    }
    drawPath(path, color)
}

/** 오른쪽 꼬리 */
private fun DrawScope.drawRightTail(color: Color) {
    val path = Path().apply {
        moveTo(0f, 0f)                      // 왼쪽 위
        lineTo(0f, size.height)             // 왼쪽 아래
        lineTo(size.width, size.height / 2) // 오른쪽 중앙 꼭지점
        close()
    }
    drawPath(path, color)
}

@Preview
@Composable
private fun PickleTooltipPreview() {
    PickleTheme {
        PickleTooltip(
            message = "메시지메시지",
            isVisible = true,
            tailPosition = TailPosition.RIGHT_TOP
        )
    }
}
