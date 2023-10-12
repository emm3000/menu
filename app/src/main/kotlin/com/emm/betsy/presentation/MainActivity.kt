package com.emm.betsy.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emm.betsy.presentation.screen.home.Home
import com.emm.betsy.presentation.screen.item.AddItem
import com.emm.betsy.presentation.screen.item.UpdateItem
import com.emm.betsy.presentation.screen.menu.ItemList
import com.emm.betsy.presentation.screen.menu.AddMenu
import com.emm.betsy.presentation.screen.menu.Menu
import com.emm.betsy.presentation.screen.orders.Orders
import com.emm.betsy.presentation.ui.theme.BetsyTheme
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as BetsyApp).container
        retrieveTokenOrFetch {
            Log.e("aea", it)
        }
        Log.e("aea", intent.extras?.getString("story_id") ?: "gaa")
        Log.e("aea", intent.extras?.getString("chu") ?: "gaa")
        setContent {

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = {

                }
            )

            DisposableEffect(key1 = Unit, effect = {
                val permissionList = mutableListOf<String>()
                permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionList.add(android.Manifest.permission.READ_MEDIA_IMAGES)
                    permissionList.add(android.Manifest.permission.READ_MEDIA_VIDEO)
                    permissionList.add(android.Manifest.permission.READ_MEDIA_AUDIO)
                    permissionList.add(android.Manifest.permission.POST_NOTIFICATIONS)
                }
                launcher.launch(permissionList.toTypedArray())
                onDispose { }
            })

            BetsyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Main()
                }
            }
        }
    }

    private fun retrieveTokenOrFetch(value: (String) -> Unit) {
        val sf = getSharedPreferences("CUSTOM_RANDOM", Context.MODE_PRIVATE)
        val fcmToken: String = sf.getString("TOKEN_KEY", "") ?: ""
        if (fcmToken.isNotEmpty()) return value(fcmToken)

        lifecycleScope.launch {
            val await = try {
                FirebaseMessaging.getInstance().token.await()
            } catch (e: Exception) {
                Log.e("aea", e.message.toString())
                ""
            }.also {
                if (it.isNotEmpty()) {
                    sf.edit { putString("TOKEN_KEY", it) }
                }
            }
            value(await)
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
        composable(NavigationRoutes.Orders.route) {
            Orders(navigationController)
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