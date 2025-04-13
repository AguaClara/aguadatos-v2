package com.example.aguadatos_v2.ui.navigation

/**
 * Class to represent each tab.
 *
 * @property route matches the tab to the correct screen
 * @property unselectedIconId represents the resource id number for the tab icon when not selected
 * @property selectedIconId represents the resource id number for the tab icon when selected
 * @property selectedRoutes represents the routes the tab should be considered selected for
 */
sealed class NavigationItem(
    val route: String,
    val unselectedIconId: Int,
    val selectedIconId: Int,
    val selectedRoutes: Set<String>
) {
    object Home : NavigationItem(
        route = Routes.HOME.route,
        unselectedIconId = R.drawable.ic_home_unselected,
        selectedIconId = R.drawable.ic_home_selected,
        selectedRoutes = setOf()
    )
    object Settings : NavigationItem(
        route = Routes.SETTINGS.route,
        unselectedIconId = R.drawable.ic_calendar_unselected,
        selectedIconId = R.drawable.ic_calendar_selected,
        selectedRoutes = setOf()
    )
    companion object {
        val bottomNavTabList = listOf(
            Home,
            Settings
        )
    }
}

/**
 * All NavUnit must have a route (which specifies where to
 * navigate to).
 */
interface NavUnit {
    val route: String
}

/**
 * Contains information about all known routes. These should correspond to routes in our
 * NavHost/new routes should be added here. Routes can exist independent of tabs (like onboarding).
 */
enum class Routes(override var route: String) : NavUnit {
    HOME("home"),
    SETTINGS("settings")
}