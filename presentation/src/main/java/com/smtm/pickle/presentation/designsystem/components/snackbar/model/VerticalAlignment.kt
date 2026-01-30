package com.smtm.pickle.presentation.designsystem.components.snackbar.model

sealed interface VerticalAlignment {
    data object Top : VerticalAlignment
    data object Bottom : VerticalAlignment
}
