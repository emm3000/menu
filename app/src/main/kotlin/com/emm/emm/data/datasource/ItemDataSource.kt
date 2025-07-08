package com.emm.emm.data.datasource

import kotlinx.coroutines.flow.Flow
import menu.item.Item

interface ItemDataSource {

    fun fetchAllItemsSortedByDate(): Flow<List<Item>>

    fun fetchItemWithId(id: Long): Flow<List<Item>>

    fun getItemsInMenu(menuId: Long = 1): Flow<List<Item>>

    fun searchItemsByName(name: String): Flow<List<Item>>

    suspend fun updateItem(name: String, type: String, imageUri: String?, id: Long)

    suspend fun deleteItem(itemId: Long)

    suspend fun insertItem(
        name: String,
        type: String,
        imageUri: String?
    )

}