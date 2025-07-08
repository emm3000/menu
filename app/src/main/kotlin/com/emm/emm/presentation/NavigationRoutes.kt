package com.emm.emm.presentation

sealed class NavigationRoutes(val route: String) {

    object Home : NavigationRoutes("home")
    object Menu : NavigationRoutes("menu")
    object ItemList : NavigationRoutes("itemList")
    object AddItem : NavigationRoutes("addItem")
    object AddMenu : NavigationRoutes("addMenu")
    object Orders : NavigationRoutes("orders")
    object UpdateItem : NavigationRoutes("updateItem/{id}") {
        fun buildRoute(itemId: Long): String {
            return "updateItem/${itemId}"
        }
    }
}
