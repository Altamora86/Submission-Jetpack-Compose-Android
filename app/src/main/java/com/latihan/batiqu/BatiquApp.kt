package com.latihan.batiqu

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.latihan.batiqu.ui.navigation.NavigationItem
import com.latihan.batiqu.ui.navigation.Screen
import com.latihan.batiqu.ui.screen.about.AboutScreen
import com.latihan.batiqu.ui.screen.detail.DetailScreen
import com.latihan.batiqu.ui.screen.favorite.FavoriteScreen
import com.latihan.batiqu.ui.screen.home.HomeScreen
import com.latihan.batiqu.ui.theme.BatiquTheme

@Composable
fun BatiquApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailBatik.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen { batikId ->
                    navController.navigate(Screen.DetailBatik.createRoute(batikId))
                }
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { batikId ->
                        navController.navigate(Screen.DetailBatik.createRoute(batikId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailBatik.route,
                arguments = listOf(
                    navArgument("batikId") { type = NavType.IntType }
                )
            ) {
                val id = it.arguments?.getInt("batikId") ?: -1
                DetailScreen(
                    batikId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }

        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors

    BottomNavigation(
        modifier = modifier,
        backgroundColor = colors.primaryVariant, // Warna latar belakang bottom bar
        contentColor = colors.onPrimary // Warna teks dan ikon
    ) {
        BottomNavigation(
            modifier = modifier
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.menu_home),
                    icon = Icons.Default.Home,
                    screen = Screen.Home
                ),
                NavigationItem(
                    title = stringResource(R.string.favorite),
                    icon = Icons.Rounded.StarBorder,
                    screen = Screen.Favorite
                ),
                NavigationItem(
                    title = stringResource(R.string.menu_profile),
                    icon = Icons.Default.AccountCircle,
                    screen = Screen.Profile
                ),
            )
            BottomNavigation {
                navigationItems.map { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BatiquAppPreview() {
    BatiquTheme {
        BatiquApp()
    }
}