@file:Suppress("UNUSED_EXPRESSION")

package com.emm.betsy.screen.menu

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emm.betsy.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddMenu(navigationController: NavHostController, vm: AddMenuViewModel = koinViewModel()) {

    val list by vm.pairList.collectAsState()

    AddMenu(
        searchText = vm.searchText,
        cleanSearchText = vm::cleanSearchText,
        onChangeSearchText = vm::updateSearchText,
        entryList = list.first,
        secondList = list.second,
        selectedList = vm.selectedItems,
        onSelectedItem = vm::addItem,
        removeItem = vm::removeItem,
        createMenu = vm::createMenu
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
private fun AddMenu(
    searchText: String = "",
    cleanSearchText: () -> Unit = {},
    onChangeSearchText: (String) -> Unit = {},
    entryList: List<Item> = emptyList(),
    secondList: List<Item> = emptyList(),
    selectedList: List<Item> = emptyList(),
    onSelectedItem: (Item) -> Unit = {},
    removeItem: (Int) -> Unit = {},
    createMenu: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
        Button(
            onClick = createMenu, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(text = "Create Menu")
        }
        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            selectedList.forEachIndexed { index, it ->
                FilterChip(
                    selected = false,
                    onClick = { },
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = when (ItemType.toItemType(it.type)) {
                            ItemType.ENTRY -> Color.Magenta
                            ItemType.SECOND -> Color.Green
                        }
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
    item: Item,
    onSelectedItem: (Item) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .height(75.dp),
        onClick = { onSelectedItem(item) },
        border = when (ItemType.toItemType(item.type)) {
            ItemType.ENTRY -> BorderStroke(1.dp, Color.Magenta)
            ItemType.SECOND -> BorderStroke(1.dp, Color.Green)
        },
    ) {
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

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun AddMenuPreview() {
    BetsyTheme(darkTheme = true) {
        Surface {
            AddMenu()
        }
    }
}