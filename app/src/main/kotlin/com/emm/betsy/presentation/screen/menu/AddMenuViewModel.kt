package com.emm.betsy.presentation.screen.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.ItemLocalDataSource
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.repository.ItemRepository
import com.emm.betsy.data.repository.MenuRepository
import com.emm.betsy.presentation.screen.menu.ItemType.ENTRY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import menu.item.Item

@OptIn(ExperimentalCoroutinesApi::class)
class AddMenuViewModel(
    private val menuRepository: MenuRepository,
    private val itemRepository: ItemRepository
) : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    val selectedItems = mutableStateListOf<ItemEntity>()

    // first value -> entry  ----- second value -> seconds
    val pairList: StateFlow<Pair<List<ItemEntity>, List<ItemEntity>>> = snapshotFlow { searchText }
        .flatMapLatest {
            itemRepository.searchItemsByName(it)
        }
        .map { listItem ->
            listItem.partition { it.type == ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

    fun updateSearchText(value: String) {
        searchText = value
    }

    fun cleanSearchText() {
        searchText = ""
    }

    fun addItem(value: ItemEntity) {
        val setOf: MutableSet<ItemEntity> = selectedItems.toMutableSet().apply {
            add(value)
        }
        selectedItems.clear()
        selectedItems.addAll(setOf)
    }

    fun removeItem(value: Int) {
        selectedItems.removeAt(value)
    }

    fun createMenu() = viewModelScope.launch {
        menuRepository.createMenu(selectedItems)
    }

}