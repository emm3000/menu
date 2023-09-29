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

class ItemDataSource(
    private val db: EmmDatabase
) {

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

    suspend fun insert(itemEntity: ItemEntity) = withContext(Dispatchers.IO) {
        try {
            itemQueries.insertItem(
                itemId = itemEntity.itemId,
                name = itemEntity.name,
                description = itemEntity.description,
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

    suspend fun insert(menuItemEntity: MenuItemEntity) = withContext(Dispatchers.IO) {
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