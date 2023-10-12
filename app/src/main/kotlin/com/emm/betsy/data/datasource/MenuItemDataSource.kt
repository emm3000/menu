package com.emm.betsy.data.datasource

interface MenuItemDataSource {

    suspend fun insert(
        menuId: Long,
        itemId: Long,
        createdAt: Long,
        updatedAt: Long
    )

    suspend fun deleteMenu(menuId: Long)

}