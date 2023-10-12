package com.emm.betsy.data.datasource

import com.emm.betsy.EmmDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import menu.menuItem.MenuItemQueries

class MenuItemLocalDataSource(
    private val db: EmmDatabase,
    private val menuItemQueries: MenuItemQueries = db.menuItemQueries
) : MenuItemDataSource {

    override suspend fun insert(
        menuId: Long,
        itemId: Long,
        createdAt: Long,
        updatedAt: Long
    ) = withContext(Dispatchers.IO) {
        try {
            menuItemQueries.insertMenuItem(
                menuId = menuId,
                itemId = itemId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteMenu(menuId: Long) = withContext(Dispatchers.IO) {
        try {
            menuItemQueries.deleteMenu(menuId = menuId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}