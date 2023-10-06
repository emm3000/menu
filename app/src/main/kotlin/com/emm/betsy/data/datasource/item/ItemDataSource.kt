package com.emm.betsy.data.datasource.item

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import com.emm.betsy.currentTime
import com.emm.betsy.data.entities.MenuEntity
import com.emm.betsy.data.entities.MenuItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import menu.item.Item
import menu.item.ItemQueries
import menu.menu.Menu
import menu.menu.MenuQueries
import menu.menuItem.MenuItemQueries
import java.time.Instant
import java.util.UUID

class ItemDataSource(db: EmmDatabase) {

    private val itemQueries: ItemQueries = db.itemQueries
    private val menuQueries: MenuQueries = db.menuQueries
    private val menuItemQueries: MenuItemQueries = db.menuItemQueries

    fun fetchItems(): Flow<List<Item>> {
        return itemQueries.getAllItems().asFlow().mapToList(Dispatchers.IO)
    }

    fun fetchMenus(): Flow<List<Menu>> {
        return menuQueries.getAllMenus().asFlow().mapToList(Dispatchers.IO)
    }

    fun fetchMenusAndItem(): Flow<List<menu.menuItem.MenuItem>> {
        return menuItemQueries.getAllMenuItems().asFlow().mapToList(Dispatchers.IO)
    }

    fun getItemsByMenu(menuId: Long = 1): Flow<List<Item>> {
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
        type: String,
        id: Long
    ) = withContext(Dispatchers.IO) {
        try {
            itemQueries.updateItem(
                name = name,
                type = type,
                updatedAt = currentTime(),
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

    suspend fun insert(
        name: String,
        type: String
    ): Unit = withContext(Dispatchers.IO) {
        try {
            itemQueries.insertItem(
                itemId = null,
                name = name,
                type = type,
                createdAt = currentTime(),
                updatedAt = currentTime()
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

    suspend fun createMenu(
        listOfItem: List<Item>
    ) {
        try {
            menuItemQueries.deleteMenu(1)
            menuQueries.insertMenu(
                date = Instant.now().toEpochMilli(),
                description = UUID.randomUUID().toString(),
                createdAt = Instant.now().toEpochMilli(),
                updatedAt = Instant.now().toEpochMilli(),
                menuId = 1
            )
            val lastMenu: Menu = menuQueries.getLastMenu()
                .asFlow()
                .mapToList(Dispatchers.IO)
                .firstOrNull()
                ?.lastOrNull() ?: return

            listOfItem.forEach {
                menuItemQueries.insertMenuItem(
                    menuId = lastMenu.menuId,
                    itemId = it.itemId,
                    createdAt = Instant.now().toEpochMilli(),
                    updatedAt = Instant.now().toEpochMilli()
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}