package com.emm.betsy.data.datasource.menu

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.emm.betsy.EmmDatabase
import com.emm.betsy.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import menu.menu.Menu
import menu.menu.MenuQueries
import java.time.Instant

class MenuDataSource(
    private val emmDatabase: EmmDatabase,
    private val menuQueries: MenuQueries = emmDatabase.menuQueries
) {

    fun getAllMenus(): Flow<List<Menu>> {
        return menuQueries
            .getAllMenus()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getLastMenuInserted(): Flow<List<Menu>> {
        return menuQueries
            .getLastMenu()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun insert(
        date: Long,
        description: String
    ) {
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

