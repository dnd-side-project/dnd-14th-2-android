package com.smtm.pickle.presentation.common.extension

import android.os.Build
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

/**
 * 카드 섀도우
 *
 * @sample com.smtm.pickle.presentation.designsystem.components.PickleCardPreview
 */
@Composable
fun Modifier.pickleShadow(
    shadowColor: Color = PickleTheme.colors.primary600,
    elevation: Dp = 50.dp
) = graphicsLayer {

    this.shadowElevation = elevation.toPx()
    this.shape = RoundedCornerShape(Dimensions.radiusSurface)
    this.clip = false

    if (Build.VERSION.SDK_INT >= 28) {
        this.ambientShadowColor = shadowColor
        this.spotShadowColor = shadowColor
    }
}
