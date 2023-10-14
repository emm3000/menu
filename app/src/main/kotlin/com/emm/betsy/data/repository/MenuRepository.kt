package com.emm.betsy.data.repository

import com.emm.betsy.data.datasource.MenuDataSource
import com.emm.betsy.data.datasource.MenuItemDataSource
import com.emm.betsy.data.entities.ItemEntity
import com.emm.betsy.presentation.currentTime
import kotlinx.coroutines.flow.firstOrNull
import menu.menu.Menu
import java.time.LocalDate
import java.time.ZoneId
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

    private fun closeToStartDate(date: LocalDate): Boolean {
        val currentDate = LocalDate.now()
        val endOfMonthDate = LocalDate.of(currentDate.year, currentDate.month, 5)

        return !date.isAfter(endOfMonthDate)
    }

    private fun closeToEndOfMoth(date: LocalDate): Boolean {
        val currentDate = LocalDate.now()
        val endOfMonthDate = LocalDate.of(currentDate.year, currentDate.month, currentDate.lengthOfMonth() - 5)

        return !date.isBefore(endOfMonthDate)
    }

    suspend fun createMenuByDate() {
        val currentDate: LocalDate = LocalDate.now()

        val firstDayOfTheMonth: LocalDate = LocalDate.of(currentDate.year, currentDate.month, 1)
        val lastDayOfTheMonth: LocalDate = LocalDate.of(currentDate.year, currentDate.month, currentDate.lengthOfMonth())

        var xxx: LocalDate = firstDayOfTheMonth

        while (xxx.isBefore(lastDayOfTheMonth) || xxx.isEqual(lastDayOfTheMonth)) {
            menuDataSource.saveMenu(
                date = xxx.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli(),
                description = xxx.toString()
            )
            xxx = xxx.plusDays(1)
        }

    }

}