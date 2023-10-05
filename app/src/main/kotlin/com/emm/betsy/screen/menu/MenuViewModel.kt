package com.emm.betsy.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import menu.item.Item

class MenuViewModel(private val itemDataSource: ItemDataSource): ViewModel() {

    val state: StateFlow<Pair<List<Item>, List<Item>>> = itemDataSource.getItemsByMenu()
        .map {
            it.partition { itemList -> itemList.type == ItemType.ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

}