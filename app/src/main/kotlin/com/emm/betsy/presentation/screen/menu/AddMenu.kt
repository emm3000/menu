@file:Suppress("UNUSED_EXPRESSION")

package com.emm.betsy.presentation.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.presentation.NavigationRoutes
import com.emm.betsy.presentation.ui.theme.BetsyTheme
import menu.menu.Menu
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun AddMenu(navigationController: NavHostController, vm: AddMenuViewModel = koinViewModel()) {

    val list: Pair<List<ItemEntity>, List<ItemEntity>> by vm.pairList.collectAsState()
    val menu: List<Menu> by vm.menu.collectAsState()

    AddMenu(
        searchText = vm.searchText,
        cleanSearchText = vm::cleanSearchText,
        onChangeSearchText = vm::updateSearchText,
        entryList = list.first,
        secondList = list.second,
        selectedList = vm.selectedItems,
        onSelectedItem = vm::addItem,
        removeItem = vm::removeItem,
        createMenu = vm::createMenu,
        navigateToAddItem = { navigationController.navigate(NavigationRoutes.AddItem.route) },
        menuList = menu
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AddMenu(
    searchText: String = "",
    cleanSearchText: () -> Unit = {},
    onChangeSearchText: (String) -> Unit = {},
    entryList: List<ItemEntity> = emptyList(),
    secondList: List<ItemEntity> = emptyList(),
    selectedList: List<ItemEntity> = emptyList(),
    onSelectedItem: (ItemEntity) -> Unit = {},
    removeItem: (Int) -> Unit = {},
    createMenu: () -> Unit = {},
    navigateToAddItem: () -> Unit = {},
    menuList: List<Menu> = emptyList()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (menuList.isNotEmpty()) {
            val initialIndex: Int = remember(Unit) {
                menuList.indexOfFirst { it.description == LocalDate.now().toString() }
            }
            val pagerState = rememberPagerState(pageCount = { (Int.MAX_VALUE / 2) + initialIndex })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                val index = it % menuList.size

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = menuList[index].description)
                    Text(text = "Como es la nuez csm yhaa")
                }

            }
        }
        FirstSection(
            modifier = Modifier
                .weight(1f),
            searchText = searchText,
            onChangeSearchText = onChangeSearchText,
            cleanSearchText = cleanSearchText,
            selectedList = selectedList,
            removeItem = removeItem,
            entryList = entryList,
            onSelectedItem = onSelectedItem,
            secondList = secondList
        )
        Divider(Modifier.height(1.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .border(
//                    width = 1.dp,
//                    color = Color.Gray,
//                    shape = RectangleShape
//                )
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = createMenu, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "Agregar menu")
            }
            Button(
                onClick = navigateToAddItem, modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = "Agregar plato")
            }
        }
    }


}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
private fun FirstSection(
    modifier: Modifier,
    searchText: String,
    onChangeSearchText: (String) -> Unit,
    cleanSearchText: () -> Unit,
    selectedList: List<ItemEntity>,
    removeItem: (Int) -> Unit,
    entryList: List<ItemEntity>,
    onSelectedItem: (ItemEntity) -> Unit,
    secondList: List<ItemEntity>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onChangeSearchText,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            placeholder = { Text(text = "Buscar plato por nombre") },
            trailingIcon = {
                if (searchText.isEmpty()) {
                    null
                } else {
                    IconButton(onClick = cleanSearchText) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                }
            }
        )
        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            selectedList.forEachIndexed { index, it ->
                FilterChip(
                    selected = false,
                    onClick = { },
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = true,
                        borderColor = when (ItemType.toItemType(it.type)) {
                            ItemType.ENTRY -> Color.Magenta.copy(0.3f)
                            ItemType.SECOND -> Color.Green.copy(0.3f)
                        },
                    ),
                    label = { Text(text = it.name) },
                    trailingIcon = {
                        Button(
                            onClick = { removeItem(index) },
                            modifier = Modifier.size(24.dp),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Caldos o entradas",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .background(MaterialTheme.colorScheme.background),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(entryList) {
                SimpleAnotherComponent(item = it, onSelectedItem)
            }
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "Segundos",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .background(MaterialTheme.colorScheme.background),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            items(secondList) {
                SimpleAnotherComponent(item = it, onSelectedItem)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAnotherComponent(
    item: ItemEntity,
    onSelectedItem: (ItemEntity) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .height(75.dp),
        onClick = { onSelectedItem(item) },
        border = when (ItemType.toItemType(item.type)) {
            ItemType.ENTRY -> BorderStroke(1.dp, Color.Magenta.copy(.4f))
            ItemType.SECOND -> BorderStroke(1.dp, Color.Green.copy(.4f))
        },
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
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    text = item.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }


    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AddMenuPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            AddMenu()
        }
    }
}