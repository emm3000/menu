package com.emm.betsy.presentation.screen.additem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.repository.ItemRepository
import com.emm.betsy.presentation.screen.menu.ItemType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EditorItemViewModel(
    private val itemId: Long,
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val channelEvent = Channel<Event>()
    val channelFlow: Flow<Event> = channelEvent.receiveAsFlow()

    var name by mutableStateOf("")
        private set

    var imageUri: String? by mutableStateOf("")
        private set

    var type by mutableStateOf(ItemType.ENTRY)
        private set

    init {
        itemRepository.fetchItemWithId(itemId)
            .map { it.getOrNull(0) }
            .onEach {
                name = it?.name ?: ""
                imageUri = it?.imageUri ?: ""
                type = ItemType.toItemType(it?.type ?: "")
            }.launchIn(viewModelScope)
    }

    fun updateName(value: String): Unit = with(value) { name = this }

    fun updateType(value: ItemType) = with(value) { type = this }

    fun updateImageUri(value: String?) {
        imageUri = value
    }

    fun addItem() = viewModelScope.launch {
        itemRepository.insertItem(
            name = name.uppercase(),
            type = type.name,
            imageUri = imageUri
        )
        channelEvent.send(Event.AddSuccess)
    }

    fun updateItem() = viewModelScope.launch {
        itemRepository.updateItem(
            name = name,
            type = type.name,
            id = itemId,
            imageUri = imageUri
        )
        channelEvent.send(Event.AddSuccess)
    }

}

sealed interface Event {
    object AddSuccess : Event
    object None : Event
}