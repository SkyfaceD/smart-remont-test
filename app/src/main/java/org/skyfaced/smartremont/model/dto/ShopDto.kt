package org.skyfaced.smartremont.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.skyfaced.smartremont.model.adapter.ShopItem

@Serializable
data class ShopDto(
    @SerialName("shop_id")
    val shopId: Int,
    @SerialName("shop_name")
    val shopName: String,
    @SerialName("icon_text")
    val iconText: String,
    @SerialName("icon_url")
    val iconUrl: String?,
    @SerialName("shop_cnt")
    val shopCnt: Int,
    @SerialName("cashback_percent_min")
    val cashbackPercentMin: Int,
    @SerialName("cashback_percent_max")
    val cashbackPercentMax: Int
) {
    fun toShopItem() = ShopItem(
        id = shopId,
        imageUrl = iconUrl.orEmpty(),
        name = shopName,
        shopCount = shopCnt.toString(),
        cashback = "$cashbackPercentMax%"
    )
}