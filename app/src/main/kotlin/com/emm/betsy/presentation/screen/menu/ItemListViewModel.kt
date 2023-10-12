package com.emm.betsy.presentation.screen.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val itemRepository: ItemRepository
) : ViewModel() {

    val allItems: StateFlow<Pair<List<ItemEntity>, List<ItemEntity>>> = itemRepository.fetchAllItemsSortedByDate()
        .map { itemList ->
            itemList.partition { it.type == ItemType.ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

    fun deleteItem(id: Long) = viewModelScope.launch {
        itemRepository.deleteItem(id)
    }

}