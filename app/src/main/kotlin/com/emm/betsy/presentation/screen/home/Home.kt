package com.emm.betsy.presentation.screen.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emm.betsy.presentation.NavigationRoutes
import com.emm.betsy.presentation.ui.theme.BetsyTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navigationController: NavController,
    vm: HomeViewModel = koinViewModel()
) {
    Home(
        navigateToAddItem = { navigationController.navigate(NavigationRoutes.ItemList.route) },
        navigateToAddMenu = { navigationController.navigate(NavigationRoutes.AddMenu.route) },
        navigateToCurrentMenu = { navigationController.navigate(NavigationRoutes.Menu.route) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
private fun Home(
    navigateToCurrentMenu: () -> Unit = {},
    navigateToAddItem: () -> Unit = {},
    navigateToAddMenu: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Menu App") },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(30.dp),
        ) {

            FilledTonalButton(
                onClick = navigateToCurrentMenu,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Current Menu")
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                onClick = navigateToAddItem,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Item")
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                onClick = navigateToAddMenu,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Menu")
            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomePreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            Home()
        }
    }
}
