package com.emm.betsy.data.entities

data class MenuEntity(
    val menuId: Long? = null,
    val date: Long,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)
