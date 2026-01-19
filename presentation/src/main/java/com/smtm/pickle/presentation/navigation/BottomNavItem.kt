package com.smtm.pickle.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.navigation.route.HomeGraphRoute
import com.smtm.pickle.presentation.navigation.route.MyPageGraphRoute
import com.smtm.pickle.presentation.navigation.route.VerdictGraphRoute
import kotlin.reflect.KClass

/**
 * @param graphRoute 탭 클릭 시 이동할 Route
 * @param graphRouteClass 현재 선택 상태 비교용
 * @param labelResId 탭 라벨
 * @param iconResId 미선택 아이콘
 * @param selectedIconResId 선택된 탭 아이콘
 */
enum class BottomNavItem(
    val graphRoute: Any,
    val graphRouteClass: KClass<*>,
    @StringRes val labelResId: Int,
    @DrawableRes val iconResId: Int,
    @DrawableRes val selectedIconResId: Int,
) {
    Home(
        graphRoute = HomeGraphRoute,
        graphRouteClass = HomeGraphRoute::class,
        labelResId = R.string.nav_home,
        iconResId = R.drawable.ic_nav_home,
        selectedIconResId = R.drawable.ic_nav_home_selected,
    ),
    VERDICT(
        graphRoute = VerdictGraphRoute,
        graphRouteClass = VerdictGraphRoute::class,
        labelResId = R.string.nav_verdict,
        iconResId = R.drawable.ic_nav_verdict,
        selectedIconResId = R.drawable.ic_nav_verdict_selected
    ),
    MY_PAGE(
        graphRoute = MyPageGraphRoute,
        graphRouteClass = MyPageGraphRoute::class,
        labelResId = R.string.nav_mypage,
        iconResId = R.drawable.ic_nav_mypage,
        selectedIconResId = R.drawable.ic_nav_mypage_selected
    ),
}