package com.emm.betsy.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import menu.item.Item

class ItemListViewModel(
    private val itemDataSource: ItemDataSource
) : ViewModel() {

    val allItems: StateFlow<Pair<List<Item>, List<Item>>> = itemDataSource.fetchItems()
        .map { itemList ->
            itemList.partition { it.type == ItemType.ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

    fun deleteItem(id: Long) = viewModelScope.launch {
        itemDataSource.deleteItem(id)
    }

}