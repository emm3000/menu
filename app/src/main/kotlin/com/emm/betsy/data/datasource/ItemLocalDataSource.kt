package com.emm.betsy.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import com.emm.betsy.presentation.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import menu.item.Item
import menu.item.ItemQueries

class ItemLocalDataSource(
    db: EmmDatabase,
    private val itemQueries: ItemQueries = db.itemQueries
) : ItemDataSource {

    override fun fetchAllItemsSortedByDate(): Flow<List<Item>> {
        return itemQueries.getAllItems().asFlow().mapToList(Dispatchers.IO)
    }

    override fun fetchItemWithId(id: Long): Flow<List<Item>> {
        return itemQueries.getItemById(id).asFlow().mapToList(Dispatchers.IO)
    }

    override fun getItemsInMenu(menuId: Long): Flow<List<Item>> {
        return itemQueries.getItemsByMenu(menuId).asFlow().mapToList(Dispatchers.IO)
    }

    override fun searchItemsByName(name: String): Flow<List<Item>> {
        return itemQueries.selectItemsByName(name).asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun updateItem(
        name: String,
        type: String,
        imageUri: String?,
        id: Long
    ) = withContext(Dispatchers.IO) {
        try {
            itemQueries.updateItem(
                name = name,
                type = type,
                updatedAt = currentTime(),
                imageUri = imageUri,
                itemId = id
            )
            Unit
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteItem(itemId: Long) = withContext(Dispatchers.IO) {
        try {
            itemQueries.deleteItem(itemId = itemId)
            Unit
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun insertItem(
        name: String,
        type: String,
        imageUri: String?
    ): Unit = withContext(Dispatchers.IO) {
        try {
            itemQueries.insertItem(
                itemId = null,
                name = name,
                type = type,
                imageUri = imageUri,
                createdAt = currentTime(),
                updatedAt = currentTime()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}