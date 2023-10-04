package com.emm.betsy.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import menu.item.Item

class ItemListViewModel(
    private val itemDataSource: ItemDataSource
) : ViewModel() {

    val allItems: StateFlow<List<Item>> = itemDataSource.fetchItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun deleteItem(id: Long) = viewModelScope.launch {
        itemDataSource.deleteItem(id)
    }

}