package com.horizon.lifeos.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.horizon.lifeos.data.LifeOSDao

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "خانه", Icons.Default.Home)
    object Focus : Screen("focus", "تمرکز", Icons.Default.Timer)
    object Finance : Screen("finance", "مالی", Icons.Default.AccountBalanceWallet)
    object Goals : Screen("goals", "اهداف", Icons.Default.Flag)
    object Planning : Screen("planning", "برنامه", Icons.Default.DateRange)
}

@Composable
fun MainScreen(dao: LifeOSDao) {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Focus, Screen.Finance, Screen.Goals, Screen.Planning)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { HomeScreen(dao) }
            composable(Screen.Focus.route) { FocusScreen(dao) }
            composable(Screen.Finance.route) { FinanceScreen(dao) }
            composable(Screen.Goals.route) { GoalsScreen(dao) }
            composable(Screen.Planning.route) { PlanningScreen(dao) }
        }
    }
}
