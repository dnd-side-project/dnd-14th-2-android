package com.smtm.pickle.presentation.home.verdict

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.smtm.pickle.presentation.common.utils.isPostNotificationsGranted
import com.smtm.pickle.presentation.common.utils.openAppSettings

@Composable
fun VerdictScreen(modifier: Modifier) {
    val context = LocalContext.current

    var showPermissionRationalDialog by rememberSaveable { mutableStateOf(false) }
    var showPermissionDeniedDialog by rememberSaveable { mutableStateOf(false) }
    var permissionRequested by rememberSaveable { mutableStateOf(false) }

    val requestNotificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showPermissionDeniedDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return@LaunchedEffect
        if (permissionRequested) return@LaunchedEffect
        if (context.isPostNotificationsGranted()) return@LaunchedEffect

        permissionRequested = true
        showPermissionRationalDialog = true
    }

    if (showPermissionRationalDialog) {
        NotificationPermissionRationaleDialog(
            onConfirm = {
                showPermissionRationalDialog = false
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            },
            onDismiss = {
                showPermissionRationalDialog = false
                showPermissionDeniedDialog = true
            },
        )
    }

    if (showPermissionDeniedDialog) {
        PermissionDeniedDialog(
            onGoToSettings = {
                showPermissionDeniedDialog = false
                context.openAppSettings()
            },
            onDismiss = {
                showPermissionDeniedDialog = false
            },
        )
    }



    VerdictContent(modifier)
}

@Composable
private fun VerdictContent(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "소비 심판 화면")
        }
    }
}

@Composable
private fun NotificationPermissionRationaleDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("알림을 받아보시겠어요?") },
        text = {
            Text("소비 심판 결과와 절약 팁을 놓치지 않도록 알림을 보내드려요.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("좋아요")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("나중에")
            }
        }
    )
}

@Composable
private fun PermissionDeniedDialog(
    onGoToSettings: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("알림을 받을 수 없어요") },
        text = {
            Text("알림을 받으시려면 설정에서 권한을 허용해주세요.")
        },
        confirmButton = {
            TextButton(onClick = onGoToSettings) {
                Text("설정으로 이동")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("괜찮아요")
            }
        }
    )
}
