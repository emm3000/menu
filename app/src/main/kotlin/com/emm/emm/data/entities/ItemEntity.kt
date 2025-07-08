package com.emm.emm.data.entities

import menu.item.Item

data class ItemEntity(
    val itemId: Long,
    val name: String,
    val type: String,
    val imageUri: String?,
    val createdAt: Long,
    val updatedAt: Long
)

private fun Item.toEntity(): ItemEntity = ItemEntity(
    itemId = itemId,
    name = name,
    type = type,
    imageUri = imageUri,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun List<Item>.toEntity(): List<ItemEntity> {
    return map(Item::toEntity)
}
