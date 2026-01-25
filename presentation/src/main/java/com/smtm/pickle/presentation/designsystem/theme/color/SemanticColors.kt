package com.smtm.pickle.presentation.designsystem.theme.color

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class SemanticColors(
    // 소셜 로그인
    val kakao: Color,
    val google: Color,

    // 소비 심판
    val guilty: Color,
    val innocent: Color,
    val guiltyBackground: Color,
    val innocentBackground: Color,

    // 상태
    val success: Color,
    val idle: Color,
)

val LightSemanticColors = SemanticColors(
    kakao = Color(0xFFFFE812),
    google = ColorPalette.base0,
    guilty = ColorPalette.error50,
    guiltyBackground = Color(0xFFFFEAEA),
    innocent = Color(0xFF5C95FF),
    innocentBackground = Color(0xFFEAF1FF),
    success = ColorPalette.primary500,
    idle = Color(0xFFFFAC46)
)

val DarkSemanticColors = LightSemanticColors

val LocalSemanticColors = staticCompositionLocalOf { LightSemanticColors }
