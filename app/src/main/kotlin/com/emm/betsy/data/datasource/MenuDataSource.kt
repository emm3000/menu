package com.emm.betsy.data.datasource

import kotlinx.coroutines.flow.Flow
import menu.menu.Menu

interface MenuDataSource {

    fun fetchAllMenus(): Flow<List<Menu>>

    fun fetchLatestMenu(): Flow<List<Menu>>

    suspend fun saveMenu(date: Long, description: String)

}