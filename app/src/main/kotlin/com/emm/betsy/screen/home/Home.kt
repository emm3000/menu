package com.emm.betsy.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emm.betsy.fastdi.AppContainer
import menu.item.Item
import menu.menu.Menu
import menu.menuItem.MenuItem
import menu.persondb.PersonEntity

@Composable
fun Home(
    appContainer: AppContainer,
    vm: HomeViewModel = viewModel(
        factory = HomeViewModel.provideInstance(appContainer.personDataSource, appContainer.itemDataSource)
    )
) {

    val lazyListState = rememberLazyListState()
    val allPerson: List<PersonEntity> by vm.allPerson.collectAsState(emptyList())
    val allMenus: List<Menu> by vm.allMenus.collectAsState(emptyList())
    val allItems: List<Item> by vm.allItems.collectAsState(emptyList())
    val allMenusAndItems: List<MenuItem> by vm.allMenusAndItems.collectAsState(emptyList())
    val itemsById: List<Item> by vm.itemsById.collectAsState(emptyList())

    LaunchedEffect(allPerson.size, allMenus.size, allItems.size, allMenusAndItems.size, itemsById.size) {
        lazyListState.animateScrollToItem(allPerson.size)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(state = lazyListState) {
            item {
                Text(text = "Todos los menus", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(allMenus) {
                Text(text = "${it.menuId} - ${it.description}")
                Spacer(modifier = Modifier.height(20.dp))

            }

            item {
                Text(text = "Todos los platos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(allItems) {
                Text(text = "${it.itemId} - ${it.name}")
                Spacer(modifier = Modifier.height(20.dp))

            }

            item {
                Text(text = "Menus y platos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(allMenusAndItems) {
                Text(text = "${it.menuId} - ${it.itemId}")
                Spacer(modifier = Modifier.height(20.dp))

            }

            item {
                Text(text = "menu 2", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(itemsById) {
                Text(text = "${it.itemId} - ${it.name}")
            }
        }
    }
}

@Composable
fun ItemsData(personEntity: PersonEntity) {

    Column {
        Text(text = "id: ${personEntity.id}", fontSize = 20.sp)
//        Text(text = "firstName: ${personEntity.firstName}")
//        Text(text = "lastname: ${personEntity.lastName}")
    }

}