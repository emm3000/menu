package com.emm.betsy.presentation.screen.menu

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.presentation.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    navigationController: NavController,
    vm: MenuViewModel = koinViewModel()
) {

    val list: Pair<List<ItemEntity>, List<ItemEntity>> by vm.state.collectAsState()

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
    startedDish: List<ItemEntity> = emptyList(),
    backgroundDish: List<ItemEntity> = emptyList(),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                Text(
                    text = "Entradas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            }
            if (startedDish.isEmpty()) {
                item {
                    Text(
                        text = "*** No selecciono el menu del día ***",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            items(startedDish) { item ->
                CurrentMenuDay(item)
            }

            item {
                Text(
                    text = "Segundos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            if (backgroundDish.isEmpty()) {
                item {
                    Text(
                        text = "*** No selecciono el menu del día ***",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            items(backgroundDish) { item ->
                CurrentMenuDay(item)
            }

        }
    }


}

@Composable
private fun CurrentMenuDay(it: ItemEntity) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if (!it.imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = it.imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(0.5f))
            )
            Text(
                text = it.name, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
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
