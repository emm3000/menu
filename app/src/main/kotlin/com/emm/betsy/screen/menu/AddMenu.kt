@file:Suppress("UNUSED_EXPRESSION")

package com.emm.betsy.screen.menu

import android.app.appsearch.AppSearchBatchResult
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emm.betsy.dateToLegibleDate
import com.emm.betsy.formatDuration
import com.emm.betsy.ui.theme.BetsyTheme
import menu.item.Item
import org.koin.androidx.compose.koinViewModel
import java.util.logging.Filter

@Composable
fun AddMenu(navigationController: NavHostController, vm: AddMenuViewModel = koinViewModel()) {

    val list by vm.pairList.collectAsState()

    AddMenu(
        searchText = vm.searchText,
        cleanSearchText = vm::cleanSearchText,
        onChangeSearchText = vm::updateSearchText,
        entryList = list.first,
        secondList = list.second,
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
        Spacer(modifier = Modifier.height(20.dp))
        FlowRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            entryList.forEach {
                FilterChip(
                    selected = false,
                    onClick = { /*TODO*/ },
                    label = { Text(text = it.name) },
                    trailingIcon = {
                        Button(
                            onClick = { /*TODO*/ },
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
                SimpleAnotherComponent(item = it)
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
                SimpleAnotherComponent(item = it)
            }
        }
//        LazyColumn(
//            state = rememberLazyListState(),
//        ) {
//            item {
//                Text(
//                    text = "Caldos o entradas",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 10.dp)
//                        .background(MaterialTheme.colorScheme.background),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    style = MaterialTheme.typography.titleMedium
//                )
//            }
//            items(entryList) {
//                SimpleAnotherComponent(item = it)
//            }
//            item {
//                Text(
//                    text = "Segundos",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 10.dp)
//                        .background(MaterialTheme.colorScheme.background),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp,
//                    style = MaterialTheme.typography.titleMedium
//                )
//            }
//            items(secondList) {
//                SimpleAnotherComponent(item = it)
//            }
//        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleAnotherComponent(
    item: Item
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxSize()
            .height(75.dp),
        onClick = {},
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