package org.skyfaced.smartremont.model.adapter

data class ShopItem(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val shopCount: String,
    val cashback: String,
) : Item
