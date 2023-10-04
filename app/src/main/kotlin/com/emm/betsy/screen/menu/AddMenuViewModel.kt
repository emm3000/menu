package com.emm.betsy.screen.menu

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.datasource.person.PersonDataSource
import com.emm.betsy.screen.menu.ItemType.ENTRY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import menu.item.Item

@OptIn(ExperimentalCoroutinesApi::class)
class AddMenuViewModel(
    private val itemSource: ItemDataSource
) : ViewModel() {

    var searchText by mutableStateOf("")
        private set

    // first value -> entry  ----- second value -> seconds
    val pairList = snapshotFlow { searchText }
        .flatMapLatest {
            itemSource.getItemsByName(it)
        }
        .map { listItem ->
            listItem.partition { it.type == ENTRY.name }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Pair(emptyList(), emptyList())
        )

    val test: StateFlow<String> = snapshotFlow { searchText }
        .map {
            "change[${it.length}] $it"
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )


    fun updateSearchText(value: String) {
        searchText = value
    }

    fun cleanSearchText() {
        searchText = ""
    }

}