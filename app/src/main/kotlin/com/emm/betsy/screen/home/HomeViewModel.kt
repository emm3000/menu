package com.emm.betsy.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.datasource.person.PersonDataSource
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.entities.MenuEntity
import com.emm.betsy.data.entities.MenuItemEntity
import com.github.javafaker.Faker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import menu.item.Item
import menu.menu.Menu
import menu.menuItem.MenuItem
import menu.persondb.PersonEntity
import java.time.Instant

class HomeViewModel(
    private val personDataSource: PersonDataSource,
    private val menuDataSource: ItemDataSource,
    private val faker: Faker
) : ViewModel() {

    val allPerson: Flow<List<PersonEntity>> = personDataSource.getAllPersons()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allMenus: StateFlow<List<Menu>> = menuDataSource.fetchMenus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allItems: StateFlow<List<Item>> = menuDataSource.fetchItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allMenusAndItems: StateFlow<List<MenuItem>> = menuDataSource.fetchMenusAndItem()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val itemsById: StateFlow<List<Item>> = menuDataSource.getItemsByMenu(2L)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
//        play()
    }

    private fun play() = viewModelScope.launch {
        menuDataSource.insert(createRandomMenu())
        delay(1000L)
        menuDataSource.insert(createRandomMenu())
        delay(1000L)
        menuDataSource.insert(createRandomMenu())
        delay(1000L)
        repeat(100) {
            menuDataSource.insert(createRandomItem())
        }

        repeat(20) {
            menuDataSource.insert(createRandomMenuItem(it))
        }

    }

    private fun createRandomItem(): ItemEntity {
        val food = faker.food().dish()
        return ItemEntity(
            name = food,
            type = faker.lorem().sentence(),
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )
    }

    private fun createRandomMenuItem(i: Int): MenuItemEntity {
        return MenuItemEntity(
            menuId = 2,
            itemId = i.toLong(),
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )
    }

    private fun createRandomMenu(): MenuEntity {
        return MenuEntity(
            date = Instant.now().toEpochMilli(),
            description = faker.lorem().sentence(),
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideInstance(personDataSource: PersonDataSource, itemDataSource: ItemDataSource): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(personDataSource, itemDataSource, Faker.instance()) as T
                }
            }
        }
    }

}
