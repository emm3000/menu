package com.emm.betsy.data.datasource.item

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.data.entities.MenuEntity
import com.emm.betsy.data.entities.MenuItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import menu.item.Item
import menu.menu.Menu
import java.time.Instant

class ItemDataSource(db: EmmDatabase) {

    private val itemQueries = db.itemQueries
    private val menuQueries = db.menuQueries
    private val menuItemQueries = db.menuItemQueries

    fun fetchItems(): Flow<List<Item>> {
        return itemQueries.getAllItems().asFlow().mapToList(Dispatchers.IO)
    }

    fun fetchMenus(): Flow<List<Menu>> {
        return menuQueries.getAllMenus().asFlow().mapToList(Dispatchers.IO)
    }

    fun fetchMenusAndItem(): Flow<List<menu.menuItem.MenuItem>> {
        return menuItemQueries.getAllMenuItems().asFlow().mapToList(Dispatchers.IO)
    }

    fun getItemsByMenu(menuId: Long): Flow<List<Item>> {
        return itemQueries.getItemsByMenu(menuId).asFlow().mapToList(Dispatchers.IO)
    }

    fun getLastMenuInserted(): Flow<List<Menu>> {
        return menuQueries.getLastMenu().asFlow().mapToList(Dispatchers.IO)
    }

    fun getItemsByName(name: String): Flow<List<Item>> {
        return itemQueries.selectItemsByName(name).asFlow().mapToList(Dispatchers.IO)
    }

    suspend fun updateItem(
        name: String,
        description: String,
        id: Long
    ) = withContext(Dispatchers.IO) {
        try {
            itemQueries.updateItem(
                name = name,
                type = description,
                updatedAt = Instant.now().toEpochMilli(),
                itemId = id
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteItem(itemId: Long) = withContext(Dispatchers.IO) {
        try {
            itemQueries.deleteItem(itemId = itemId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insert(itemEntity: ItemEntity): Unit = withContext(Dispatchers.IO) {
        try {
            itemQueries.insertItem(
                itemId = itemEntity.itemId,
                name = itemEntity.name,
                type = itemEntity.type,
                createdAt = itemEntity.createdAt,
                updatedAt = itemEntity.updatedAt
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insert(menuEntity: MenuEntity) = withContext(Dispatchers.IO) {
        try {
            menuQueries.insertMenu(
                menuId = menuEntity.menuId,
                date = menuEntity.date,
                description = menuEntity.description,
                createdAt = menuEntity.createdAt,
                updatedAt = menuEntity.updatedAt
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun insert(menuItemEntity: MenuItemEntity): Unit = withContext(Dispatchers.IO) {
        try {
            menuItemQueries.insertMenuItem(
                menuId = menuItemEntity.menuId,
                itemId = menuItemEntity.itemId,
                createdAt = menuItemEntity.createdAt,
                updatedAt = menuItemEntity.updatedAt
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}