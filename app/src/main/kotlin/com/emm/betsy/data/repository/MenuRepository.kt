package com.emm.betsy.data.repository

import com.emm.betsy.data.datasource.MenuDataSource
import com.emm.betsy.data.datasource.MenuItemDataSource
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.presentation.currentTime
import kotlinx.coroutines.flow.firstOrNull
import menu.menu.Menu
import java.util.UUID

class MenuRepository(
    private val menuDataSource: MenuDataSource,
    private val menuItemDataSource: MenuItemDataSource,
) {

    suspend fun createMenu(listOfItem: List<ItemEntity>) {
        menuItemDataSource.deleteMenu(1)

        menuDataSource.saveMenu(
            date = currentTime(),
            description = UUID.randomUUID().toString()
        )

        val lastMenus: Menu = menuDataSource
            .fetchLatestMenu()
            .firstOrNull()
            ?.lastOrNull() ?: return

        listOfItem.forEach {
            menuItemDataSource.insert(
                menuId = lastMenus.menuId,
                itemId = it.itemId,
                createdAt = currentTime(),
                updatedAt = currentTime()
            )
        }

    }

}