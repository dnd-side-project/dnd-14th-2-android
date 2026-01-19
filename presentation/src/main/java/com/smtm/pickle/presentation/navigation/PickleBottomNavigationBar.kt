package com.smtm.pickle.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun PickleBottomNavigationBar(
    currentDestination: NavDestination?,
    onNavigate: (Any) -> Unit,
) {
    NavigationBar {
        BottomNavItem.entries.forEach { item ->
            // 현재 destination이 이 탭의 Graph에 속하는지 확인
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(item.graphRouteClass)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(item.graphRoute)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (isSelected) item.selectedIconResId else item.iconResId
                        ),
                        contentDescription = stringResource(item.labelResId)
                    )
                },
                label = {
                    Text(text = stringResource(item.labelResId))
                },
                alwaysShowLabel = true
            )
        }
    }
}