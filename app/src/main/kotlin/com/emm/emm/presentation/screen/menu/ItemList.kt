package com.emm.emm.presentation.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.emm.emm.data.entities.ItemEntity
import com.emm.emm.presentation.NavigationRoutes
import com.emm.emm.presentation.components.BaseScaffold
import com.emm.emm.presentation.ui.theme.EmmTheme
import kotlinx.coroutines.delay
import menu.item.Item
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun ItemList(navigationController: NavHostController, vm: ItemListViewModel = koinViewModel()) {

    val itemList: Pair<List<ItemEntity>, List<ItemEntity>> by vm.allItems.collectAsState()

    ItemList(
        itemListEntry = itemList.first,
        itemListSecond = itemList.second,
        deleteItem = vm::deleteItem,
        popBackStack = { navigationController.popBackStack() },
        navigateToAddItem = { navigationController.navigate(NavigationRoutes.AddItem.route) },
        navigateToUpdateItem = {
            navigationController.navigate(NavigationRoutes.UpdateItem.buildRoute(it.itemId))
        }
    )

}

@Composable
private fun ItemList(
    itemListEntry: List<ItemEntity> = emptyList(),
    itemListSecond: List<ItemEntity> = emptyList(),
    deleteItem: (Long) -> Unit = {},
    popBackStack: () -> Unit = {},
    navigateToAddItem: () -> Unit = {},
    navigateToUpdateItem: (ItemEntity) -> Unit = {}
) {

    BaseScaffold(
        popBackStack = popBackStack,
        title = "Lista de platos",
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddItem) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            columns = GridCells.Fixed(2),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (itemListEntry.isEmpty() && itemListSecond.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Sin items", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Entradas o caldos", modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }

            items(itemListEntry, key = { it.itemId }) { item ->
                AnotherComponent(
                    item = item,
                    deleteItem = deleteItem,
                    updateItem = navigateToUpdateItem
                )
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Segundos", modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }

            items(itemListSecond, key = { it.itemId }) { item ->
                AnotherComponent(
                    item = item,
                    deleteItem = deleteItem,
                    updateItem = navigateToUpdateItem
                )
            }

            item {
                Spacer(modifier = Modifier.height(70.dp))
            }

        }
    }

}

@Composable
fun RandomComponent(item: Item) {

    val ajaj: MutableState<String?> = remember {
        mutableStateOf(item.imageUri)
    }

    LaunchedEffect(key1 = Unit) {
        delay(200L)
        ajaj.value = item.imageUri
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .border(1.dp, Color.White, RoundedCornerShape(10))
    ) {
        AsyncImage(
            model = item.imageUri,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10))
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnotherComponent(
    item: ItemEntity,
    deleteItem: (Long) -> Unit,
    updateItem: (ItemEntity) -> Unit
) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .height(140.dp),
        onClick = {},
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = item.imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(0.5f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = item.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = ItemType.toItemType(item.type).label, overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    Row {
                        IconButton(onClick = { deleteItem.invoke(item.itemId) }) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                        }
                        IconButton(onClick = {
                            updateItem.invoke(item)
                        }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                        }
                    }

                }
            }
        }


    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ItemListPreview() {
    EmmTheme(darkTheme = true) {
        Surface {
            val generateItems = List(5) {
                ItemEntity(
                    itemId = Random.nextLong(),
                    name = "Random String $it",
                    type = "Random description $it",
                    createdAt = Random.nextLong(),
                    updatedAt = Random.nextLong(),
                    imageUri = null
                )
            }
            ItemList(
                itemListEntry = generateItems,
                itemListSecond = generateItems
            )
        }
    }
}