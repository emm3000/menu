package com.emm.betsy.data.entities

data class ItemEntity(
    val itemId: Long? = null,
    val name: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)
