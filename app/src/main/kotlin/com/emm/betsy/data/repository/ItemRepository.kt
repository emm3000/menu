package com.emm.betsy.data.repository

import com.emm.betsy.data.datasource.ItemDataSource
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.entities.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import menu.item.Item

class ItemRepository(private val itemDataSource: ItemDataSource) {

    fun fetchItemWithId(itemId: Long): Flow<List<ItemEntity>> {
        return itemDataSource.fetchItemWithId(itemId)
            .map(List<Item>::toEntity)
    }

    suspend fun insertItem(name: String, type: String, imageUri: String?) {
        itemDataSource.insertItem(name, type, imageUri)
    }

    suspend fun updateItem(name: String, type: String, id: Long, imageUri: String?) {
        itemDataSource.updateItem(name, type, imageUri, id)
    }

    suspend fun deleteItem(id: Long) {
        itemDataSource.deleteItem(id)
    }

    fun searchItemsByName(name: String): Flow<List<ItemEntity>> {
        return itemDataSource.searchItemsByName(name = name)
            .map(List<Item>::toEntity)
    }

    fun fetchAllItemsSortedByDate(): Flow<List<ItemEntity>> {
        return itemDataSource.fetchAllItemsSortedByDate()
            .map(List<Item>::toEntity)
    }

    fun getItemsInMenu(menuId: Long): Flow<List<ItemEntity>> {
        return itemDataSource.getItemsInMenu(menuId)
            .map(List<Item>::toEntity)
    }

}