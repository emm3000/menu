package com.emm.betsy.presentation.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MenuViewModel(itemRepository: ItemRepository) : ViewModel() {

    val state: StateFlow<Pair<List<ItemEntity>, List<ItemEntity>>> = itemRepository.getItemsInMenu(1)
        .map {
            it.partition { itemList -> itemList.type == ItemType.ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

}