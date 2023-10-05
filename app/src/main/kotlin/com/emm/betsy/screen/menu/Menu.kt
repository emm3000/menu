package com.emm.betsy.screen.menu

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emm.betsy.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    navigationController: NavController,
    vm: MenuViewModel = koinViewModel()
) {

    val list: Pair<List<Item>, List<Item>> by vm.state.collectAsState()

    Menu(
        popBackStack = { navigationController.popBackStack() },
        startedDish = list.first,
        backgroundDish = list.second
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
private fun Menu(
    startedDish: List<Item> = emptyList(),
    backgroundDish: List<Item> = emptyList(),
    popBackStack: () -> Unit = {}
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Rosa Menu") },
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(30.dp),
        ) {

            Spacer(modifier = Modifier.height(22.dp))

            Text(text = "Entradas", style = MaterialTheme.typography.titleMedium)

            if (startedDish.isEmpty()) {
                Text(
                    text = "*** No selecciono el menu del día ***",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                startedDish.forEach {
                    Text(text = "- ${it.name}")
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(text = "Segundos", style = MaterialTheme.typography.titleMedium)

            if (backgroundDish.isEmpty()) {
                Text(
                    text = "*** No selecciono el menu del día ***",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                backgroundDish.forEach {
                    Text(text = "- ${it.name}")
                }
            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MenuPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            Menu()
        }
    }
}
