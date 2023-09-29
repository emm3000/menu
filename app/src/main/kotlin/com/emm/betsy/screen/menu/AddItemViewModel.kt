package com.emm.betsy.screen.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.entities.ItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import menu.item.Item
import java.time.Instant

class AddItemViewModel(
    private val itemDataSource: ItemDataSource
) : ViewModel() {

    var name by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    val allItems: StateFlow<List<Item>> = itemDataSource.fetchItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addItem() = viewModelScope.launch {
        val entity = ItemEntity(
            name = name,
            description = description,
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )
        itemDataSource.insert(entity)
        cleanValues()
    }

    private fun cleanValues() {
        name = ""
        description = ""
    }

    fun updateName(value: String) {
        name = value
    }

    fun updateDescription(value: String) {
        description = value
    }

}