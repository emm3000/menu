package com.emm.betsy

sealed class NavigationRoutes(val route: String) {

    object Home : NavigationRoutes("home")
    object Menu : NavigationRoutes("menu")
    object ItemList : NavigationRoutes("itemList")
    object AddItem : NavigationRoutes("addItem")
    object AddMenu : NavigationRoutes("addMenu")
    object UpdateItem : NavigationRoutes("updateItem/{id}?name={name}&type={type}&image{image}") {
        fun buildRoute(name: String, type: String, itemId: Long, image: String?): String {
            return "updateItem/${itemId}?name=${name}&type=${type}&image=${image}"
        }
    }
}
