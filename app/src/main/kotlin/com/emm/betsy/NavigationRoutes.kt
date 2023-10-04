package com.emm.betsy

sealed class NavigationRoutes(val route: String) {

    object Home: NavigationRoutes("home")
    object Menu: NavigationRoutes("menu")
    object ItemList: NavigationRoutes("itemList")
    object AddItem: NavigationRoutes("addItem")
    object AddMenu: NavigationRoutes("addMenu")
}
