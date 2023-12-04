package com.latihan.batiqu.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailBatik : Screen("home/{batikId}") {
        fun createRoute(batikId: Int) = "home/$batikId"
    }
}

