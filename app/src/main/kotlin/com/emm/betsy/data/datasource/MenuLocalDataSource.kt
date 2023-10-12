package com.emm.betsy.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import com.emm.betsy.presentation.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import menu.menu.Menu
import menu.menu.MenuQueries

class MenuLocalDataSource(
    private val emmDatabase: EmmDatabase,
    private val menuQueries: MenuQueries = emmDatabase.menuQueries
) : MenuDataSource {

    override fun fetchAllMenus(): Flow<List<Menu>> {
        return menuQueries
            .getAllMenus()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override fun fetchLatestMenu(): Flow<List<Menu>> {
        return menuQueries
            .getLastMenu()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    override suspend fun saveMenu(date: Long, description: String) = withContext(Dispatchers.IO) {
        try {
            menuQueries.insertMenu(
                menuId = null,
                date = date,
                description = description,
                createdAt = currentTime(),
                updatedAt = currentTime()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

