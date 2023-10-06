package com.emm.betsy.screen.additem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.screen.menu.ItemType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditorItemViewModel(
    nameFromBundle: String,
    typeFromBundle: String,
    private val itemId: Long,
    private val itemDataSource: ItemDataSource
) : ViewModel() {

    private val channelEvent = Channel<Event>()
    val channelFlow: Flow<Event> = channelEvent.receiveAsFlow()

    var name by mutableStateOf(nameFromBundle)
        private set

    var type by mutableStateOf(ItemType.toItemType(typeFromBundle))
        private set

    fun updateName(value: String): Unit = with(value) { name = this }

    fun updateType(value: ItemType) = with(value) { type = this }

    fun addItem() = viewModelScope.launch {
        itemDataSource.insert(
            name = name.uppercase(),
            type = type.name
        )
        channelEvent.send(Event.AddSuccess)
    }

    fun updateItem() = viewModelScope.launch {
        itemDataSource.updateItem(
            name = name,
            type = type.name,
            id = itemId
        )
        channelEvent.send(Event.AddSuccess)
    }

}

sealed interface Event {
    object AddSuccess : Event
    object None : Event
}