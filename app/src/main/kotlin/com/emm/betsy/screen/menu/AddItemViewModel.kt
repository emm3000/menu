package com.emm.betsy.screen.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.entities.ItemEntity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant

class AddItemViewModel(
    private val itemDataSource: ItemDataSource
): ViewModel() {

    private val channelEvent = Channel<Event>()
    val channelFlow: Flow<Event> = channelEvent.receiveAsFlow()

    var name by mutableStateOf("")
        private set

    var type by mutableStateOf(ItemType.ENTRY)
        private set

    fun updateName(value: String): Unit = with(value) { name = this }

    fun updateType(value: ItemType) = with(value) { type = this }

    fun addItem() = viewModelScope.launch {
        val entity = ItemEntity(
            name = name,
            type = type.name,
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )
        itemDataSource.insert(entity)
        channelEvent.send(Event.AddSuccess)
    }

}

sealed interface Event {
    object AddSuccess: Event
    object None: Event
}