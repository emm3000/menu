package com.emm.betsy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emm.betsy.screen.home.Home
import com.emm.betsy.screen.additem.AddItem
import com.emm.betsy.screen.additem.UpdateItem
import com.emm.betsy.screen.menu.ItemList
import com.emm.betsy.screen.menu.AddMenu
import com.emm.betsy.screen.menu.Menu
import com.emm.betsy.ui.theme.BetsyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as BetsyApp).container
        setContent {
            BetsyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {

    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = NavigationRoutes.Home.route) {
        composable(NavigationRoutes.Home.route) {
            Home(navigationController)
        }
        composable(NavigationRoutes.Menu.route) {
            Menu(navigationController)
        }
        composable(NavigationRoutes.ItemList.route) {
            ItemList(navigationController)
        }
        composable(NavigationRoutes.AddMenu.route) {
            AddMenu(navigationController)
        }
        composable(NavigationRoutes.AddItem.route) {
            AddItem(navigationController)
        }
        composable(
            route = NavigationRoutes.UpdateItem.route,
            arguments = listOf(navArgument("id") { defaultValue = 0L })
        ) { backStackEntry ->
            val id: Long = backStackEntry.arguments?.getLong("id") ?: 0L

            UpdateItem(
                navController = navigationController,
                id = id,
            )
        }
    }
}