package com.emm.emm.presentation.screen.menu

enum class ItemType(val label: String) {
    ENTRY("Caldo o Entrada"),
    SECOND("Segundos");

    companion object {
        fun toItemType(name: String): ItemType = when(name) {
            "ENTRY" -> ENTRY
            "SECOND" -> SECOND
            else -> ENTRY
        }
    }
}