package com.emm.betsy.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emm.betsy.NavigationRoutes
import com.emm.betsy.components.BaseScaffold
import com.emm.betsy.dateToLegibleDate
import com.emm.betsy.formatDuration
import com.emm.betsy.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun ItemList(navigationController: NavHostController, vm: ItemListViewModel = koinViewModel()) {

    val itemList: List<Item> by vm.allItems.collectAsState()

    ItemList(
        itemList = itemList,
        deleteItem = vm::deleteItem,
        popBackStack = { navigationController.popBackStack() },
        navigateToAddItem = { navigationController.navigate(NavigationRoutes.AddItem.route) }
    )

}

@Composable
private fun ItemList(
    itemList: List<Item> = emptyList(),
    deleteItem: (Long) -> Unit = {},
    popBackStack: () -> Unit = {},
    navigateToAddItem: () -> Unit = {}
) {

    BaseScaffold(
        popBackStack = popBackStack,
        title = "Add item",
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddItem) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            state = rememberLazyListState(),
            contentPadding = it
        ) {
            if (itemList.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Sin items", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }
            }
            items(itemList) { item ->
                AnotherComponent(
                    item = item,
                    deleteItem = deleteItem
                )
            }
            
            item { 
                Spacer(modifier = Modifier.height(70.dp))
            }

        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnotherComponent(
    item: Item,
    deleteItem: (Long) -> Unit,
) {

    val randomColor = rememberSaveable(stateSaver = listSaver<Color, Any>(
        save = { listOf(it.red, it.green, it.blue) },
        restore = { Color(it[0] as Float, it[1] as Float, it[2] as Float) }
    )) {
        mutableStateOf(
            Color(
                red = Random.nextFloat(),
                green = Random.nextFloat(),
                blue = Random.nextFloat(),
            )
        )
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp),
        onClick = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Min),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .weight(1f)
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
                Text(text = item.createdAt.dateToLegibleDate())
                Text(text = item.createdAt.formatDuration())
            }
            Box(
                modifier = Modifier
                    .background(randomColor.value)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                Column {
                    IconButton(onClick = { deleteItem.invoke(item.itemId) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                    }
                }

            }
        }

    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ItemListPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            val generateItems = List(5) {
                Item(
                    itemId = Random.nextLong(),
                    name = "Random String $it",
                    type = "Random description $it",
                    createdAt = Random.nextLong(),
                    updatedAt = Random.nextLong()
                )
            }
            ItemList(
                itemList = generateItems
            )
        }
    }
}